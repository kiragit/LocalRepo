/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/
package org.example.browserview;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.webkit.WebView;
// ...

import android.widget.Button;
import android.widget.EditText;


public class BrowserView extends Activity {
   
   private EditText urlText;
   private Button goButton;
   
   private WebView webView;
   // ...
   

   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      // ...
      
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      // すべてのユーザインタフェース要素のハンドルを取得する
      urlText = (EditText) findViewById(R.id.url_field);
      goButton = (Button) findViewById(R.id.go_button);
      
      webView = (WebView) findViewById(R.id.web_view);
      // ...
      

      // イベントハンドラを設定する
      goButton.setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
            openBrowser();
         }
      });
      urlText.setOnKeyListener(new OnKeyListener() {
         public boolean onKey(View view, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
               openBrowser();
               return true;
            }
            return false;
         }
      });
      
   }
   

   
   /** ブラウザをオープンし、テキストボックスで指定されたURL にジャンプする */
   private void openBrowser() {
      webView.loadUrl(urlText.getText().toString());
      webView.requestFocus();
   }
   
   
}
