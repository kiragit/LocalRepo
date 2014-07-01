/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.translate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Translate extends Activity {
   private Spinner fromSpinner;
   private Spinner toSpinner;
   private EditText origText;
   private TextView transText;
   private TextView retransText;

   private TextWatcher textWatcher;
   private OnItemSelectedListener itemListener;

   private Handler guiThread;
   private ExecutorService transThread;
   private Runnable updateTask;
   private Future transPending;

   @Override
   public void onCreate(Bundle savedInstanceState) { 
      super.onCreate(savedInstanceState);

      setContentView(R.layout.main);
      initThreading();
      findViews(); 
      setAdapters(); 
      setListeners(); 
   }
   

   @Override
   protected void onDestroy() {
      // Terminate extra threads here
      transThread.shutdownNow();
      super.onDestroy();
   }

   
   /** すべてのユーザインタフェース要素のハンドルを取得する */
   private void findViews() {
      fromSpinner = (Spinner) this.findViewById(R.id.from_language);
      toSpinner = (Spinner) this.findViewById(R.id.to_language);
      origText = (EditText) this.findViewById(R.id.original_text);
      transText = (TextView) this.findViewById(R.id.translated_text);
      retransText = (TextView) this.findViewById(R.id.retranslated_text);
   }
   

   
   /** スピーナーのソースを定義する */
   private void setAdapters() {
      // リソースからスピナーのリストを構築する。
      // スピナーユーザインタフェースは標準レイアウトを使う
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this, R.array.languages,
            android.R.layout.simple_spinner_item);
      adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item);
      fromSpinner.setAdapter(adapter);
      toSpinner.setAdapter(adapter);

      // 自動的に2個のスピナーアイテムを選択する
      fromSpinner.setSelection(8); // English (en)
      toSpinner.setSelection(11);  // French (fr)
   }
   

   
   /** インタフェースイベントハンドラを設定する */
   private void setListeners() {
      // イベントリスナーを定義する
      textWatcher = new TextWatcher() {
         public void beforeTextChanged(CharSequence s, int start,
               int count, int after) {
            /* 何もしない */
         }
         public void onTextChanged(CharSequence s, int start,
               int before, int count) {
            queueUpdate(1000 /* m秒 */);
         }
         public void afterTextChanged(Editable s) {
            /* 何もしない */
         }
      };
      itemListener = new OnItemSelectedListener() {
         public void onItemSelected(AdapterView parent, View v,
               int position, long id) {
            queueUpdate(200 /* m秒 */);
         }
         public void onNothingSelected(AdapterView parent) {
            /* 何もしない */
         }
      };

      // GUIウィジェットのリスナーを設定する
      origText.addTextChangedListener(textWatcher);
      fromSpinner.setOnItemSelectedListener(itemListener);
      toSpinner.setOnItemSelectedListener(itemListener);
   }
   

   
   /**
    * マルチスレッド実行を初期化する。スレッドは2つある。
    * 1) Androidによってすでに起動されているメインGUIスレッド
    * 2) Executorを使って起動する翻訳スレッド
    */
   private void initThreading() {
      guiThread = new Handler();
      transThread = Executors.newSingleThreadExecutor();

      // このタスクが翻訳と画面変更を行う
      updateTask = new Runnable() { 
         public void run() {
            // 翻訳すべきテキストを取得する
            String original = origText.getText().toString().trim();

            // すでに翻訳がある場合、それをキャンセルする
            if (transPending != null)
               transPending.cancel(true); 

            // 簡単な条件の処理
            if (original.length() == 0) {
               transText.setText(R.string.empty); 
               retransText.setText(R.string.empty);
            } else {
               // ユーザに処理をしているということを知らせる
               transText.setText(R.string.translating); 
               retransText.setText(R.string.translating);

               // 翻訳を開始するが終了を待たない
               try {
                  TranslateTask translateTask = new TranslateTask( 
                        Translate.this, // アクティビティの参照
                        original, // 元のテキスト
                        getLang(fromSpinner), // 最初の言語
                        getLang(toSpinner) // 第2の言語
                  );
                  transPending = transThread.submit(translateTask); 
               } catch (RejectedExecutionException e) {
                  // 新しいタスクを起動できない
                  transText.setText(R.string.translation_error); 
                  retransText.setText(R.string.translation_error);
               }
            }
         }
      };
   }
   

   
   /** 現在のスピナー項目から言語コードを抽出する */
   private String getLang(Spinner spinner) {
      String result = spinner.getSelectedItem().toString();
      int lparen = result.indexOf('(');
      int rparen = result.indexOf(')');
      result = result.substring(lparen + 1, rparen);
      return result;
   }

   /** 短いディレイの後に更新を開始するよう要求する */
   private void queueUpdate(long delayMillis) {
      // まだ開始されていない古い更新をキャンセルする
      guiThread.removeCallbacks(updateTask);
      // ディレイの間何もなければ更新を開始する
      guiThread.postDelayed(updateTask, delayMillis);
   }

   /** 画面上のテキストを書き換える（他のスレッドから呼び出される） */
   public void setTranslated(String text) {
      guiSetText(transText, text);
   }

   /** 画面上のテキストを書き換える（他のスレッドから呼び出される） */
   public void setRetranslated(String text) {
      guiSetText(retransText, text);
   }

   /** GUIに対するすべての変更は、GUIスレッド内で実行しなければならない */
   private void guiSetText(final TextView view, final String text) {
      guiThread.post(new Runnable() {
         public void run() {
            view.setText(text);
         }
      });
   }
   
   
}
