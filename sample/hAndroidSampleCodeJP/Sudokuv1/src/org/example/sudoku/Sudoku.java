/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/
package org.example.sudoku;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import android.view.View;
import android.view.View.OnClickListener;



public class Sudoku extends Activity implements OnClickListener {
   
   
   private static final String TAG = "Sudoku";
   
   
   
   /** アクティビティが最初に作成されたときに呼び出される */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      // すべてのボタンについてクリックリスナーをセットアップする
      View continueButton = this.findViewById(R.id.continue_button);
      continueButton.setOnClickListener(this);
      View newButton = this.findViewById(R.id.new_button);
      newButton.setOnClickListener(this);
      View aboutButton = this.findViewById(R.id.about_button);
      aboutButton.setOnClickListener(this);
      View exitButton = this.findViewById(R.id.exit_button);
      exitButton.setOnClickListener(this);
   }
   

   
   // ...
   public void onClick(View v) {
      switch (v.getId()) {
      case R.id.about_button:
         Intent i = new Intent(this, About.class);
         startActivity(i);
         break;
      // 他のボタンはここへ（もしあれば）
      
      
      case R.id.new_button:
         openNewGameDialog();
         break;
      
      
      case R.id.exit_button:
         finish();
         break;
      
      
      }
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return true;
   }
   

   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.settings:
         startActivity(new Intent(this, Settings.class));
         return true;
      // 他の項目はここへ（もしあれば）
      }
      return false;
   }
   

   
   /** ユーザにどの難易度レベルを使うかを尋ねる */
   private void openNewGameDialog() {
      new AlertDialog.Builder(this)
           .setTitle(R.string.new_game_title)
           .setItems(R.array.difficulty,
            new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialoginterface,
                     int i) {
                  startGame(i);
               }
            })
           .show();
   }
   
   /** 指定された難易度レベルで新しいゲームを開始する */
   private void startGame(int i) {
      Log.d(TAG, "clicked on " + i);
      // ここでゲームを開始する
   }
   
   
}
