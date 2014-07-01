/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/
package org.example.translate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class TranslateTask implements Runnable {
   private static final String TAG = "TranslateTask";
   private final Translate translate;
   private final String original, from, to;

   TranslateTask(Translate translate, String original, String from,
         String to) {
      this.translate = translate;
      this.original = original;
      this.from = from;
      this.to = to;
   }

   public void run() {
      // 最初の言語から第2の言語へと翻訳する
      String trans = doTranslate(original, from, to);
      translate.setTranslated(trans);

      // 次に、最初の言語から返ってきたものを翻訳する
      // 理想的には、それは同一であるべきだが、通常はそうではない
      String retrans = doTranslate(trans, to, from); // 入替える
      translate.setRetranslated(retrans);
   }

   /**
    * Google Translation APIを呼び出して翻訳する。
    * APIについての詳細は以下を参照:
    * http://code.google.com/apis/ajaxlanguage
    */
   private String doTranslate(String original, String from,
         String to) {
      String result = translate.getResources().getString(
            R.string.translation_error);
      HttpURLConnection con = null;
      Log.d(TAG, "doTranslate(" + original + ", " + from + ", "
            + to + ")");

      try {
         // タスクが中断されていないかをチェックする
         if (Thread.interrupted())
            throw new InterruptedException();

         // Google API用にRESTのクエリーを作る
         String q = URLEncoder.encode(original, "UTF-8");
         URL url = new URL(
               "http://ajax.googleapis.com/ajax/services/language/translate"
                     + "?v=1.0" + "&q=" + q + "&langpair=" + from
                     + "%7C" + to);
         con = (HttpURLConnection) url.openConnection();
         con.setReadTimeout(10000 /* m秒 */);
         con.setConnectTimeout(15000 /* m秒 */);
         con.setRequestMethod("GET");
         con.addRequestProperty("Referer",
               "http://www.pragprog.com/titles/eband/hello-android");
         con.setDoInput(true);

         // クエリーを開始
         con.connect();

         // タスクが中断されていないかをチェックする
         if (Thread.interrupted())
            throw new InterruptedException();

         // クエリーから処理結果を読み込む
         InputStreamReader reader = new InputStreamReader(
               con.getInputStream(), "UTF-8");
         int nbytes = con.getContentLength();
         char[] buffer = new char[nbytes]; // #chars <= #bytes
         int nchars = reader.read(buffer);
         String payload = String.valueOf(buffer, 0, nchars);
         reader.close();

         // Parse to get translated text
         JSONObject jsonObject = new JSONObject(payload);
         result = jsonObject.getJSONObject("responseData")
               .getString("translatedText")
               .replace("&#39;", "'")
               .replace("&amp;", "&");

         // タスクが中断されていないかをチェックする
         if (Thread.interrupted())
            throw new InterruptedException();

      } catch (IOException e) {
         Log.e(TAG, "IOException", e);
      } catch (JSONException e) {
         Log.e(TAG, "JSONException", e);
      } catch (InterruptedException e) {
         Log.d(TAG, "InterruptedException", e);
         result = translate.getResources().getString(
               R.string.translation_interrupted);
      } finally {
         if (con != null) {
            con.disconnect();
         }
      }

      // すべてが完了
      Log.d(TAG, "   -> returned " + result);
      return result;
   }
}
