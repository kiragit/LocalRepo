/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.localbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocalBrowser extends Activity {
   private static final String TAG = "LocalBrowser";
   private final Handler handler = new Handler(); 
   private WebView webView;
   private TextView textView;
   private Button button;

   
   
   /** JavaScript にエクスポートされるオブジェクト */
   private class AndroidBridge {
      public void callAndroid(final String arg) { // must be final
         handler.post(new Runnable() {
            public void run() {
               Log.d(TAG, "callAndroid(" + arg + ")");
               textView.setText(arg);
            }
         });
      }
   }
   

   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      // 画面上のAndroidコントロールを探す
      webView = (WebView) findViewById(R.id.web_view);
      textView = (TextView) findViewById(R.id.text_view);
      button = (Button) findViewById(R.id.button);
      // onCreateの続き...
      

      
      // 組み込みブラウザでJavaScriptをオンにする
      webView.getSettings().setJavaScriptEnabled(true);

      // ブラウザのJavaScriptにJavaオブジェクトを登録する
      webView.addJavascriptInterface(new AndroidBridge(),
            "android");
      

      
      // JavaScriptがアラートウィンドウをオープンしよう
      // としたときに、呼び出される関数を設定する
      webView.setWebChromeClient(new WebChromeClient() {
         @Override
         public boolean onJsAlert(final WebView view,
               final String url, final String message,
               JsResult result) {
            Log.d(TAG, "onJsAlert(" + view + ", " + url + ", "
                  + message + ", " + result + ")");
            Toast.makeText(LocalBrowser.this, message, 3000).show();
            return false;
         }
      });
      

      
      // ローカルのassetディレクトリからWebページをロードする
      webView.loadUrl("file:///android_asset/index.html");
      

      
      // この関数は、ユーザがAndroid側のボタンを押したときに呼び出される
      button.setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
            Log.d(TAG, "onClick(" + view + ")");
            webView.loadUrl("javascript:callJS('Hello from Android')");
         }
      });
      
      
   }
}
