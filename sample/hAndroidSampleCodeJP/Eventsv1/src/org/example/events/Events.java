/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.events;

import static android.provider.BaseColumns._ID;
import static org.example.events.Constants.TABLE_NAME;
import static org.example.events.Constants.TIME;
import static org.example.events.Constants.TITLE;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Events extends Activity {
   
   
   private static String[] FROM = { _ID, TIME, TITLE, };
   private static String ORDER_BY = TIME + " DESC";
   
   
   private EventsData events;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main); 
      events = new EventsData(this); 
      try {
         addEvent("Hello, Android!"); 
         Cursor cursor = getEvents(); 
         showEvents(cursor); 
      } finally {
         events.close(); 
      }
   }
   

   
   private void addEvent(String string) {
      // Eventsデータソースに新しいレコードを挿入する。
      // 削除、更新も同様の方法で実行できる
      SQLiteDatabase db = events.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(TIME, System.currentTimeMillis());
      values.put(TITLE, string);
      db.insertOrThrow(TABLE_NAME, null, values);
   }
   

   
   private Cursor getEvents() {
      // 管理されたクエリーを実行する。Activityは、クローズの他、
      // 必要な場合は再クエリーを処理する
      SQLiteDatabase db = events.getReadableDatabase();
      Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
            null, ORDER_BY);
      startManagingCursor(cursor);
      return cursor;
   }
   

   
   private void showEvents(Cursor cursor) {
      // カーソルのすべての内容を文字列に詰め込む
      StringBuilder builder = new StringBuilder( 
            "Saved events:\n");
      while (cursor.moveToNext()) { 
         // インデックスの取得にはgetColumnIndexOrThrow()も使える
         long id = cursor.getLong(0); 
         long time = cursor.getLong(1);
         String title = cursor.getString(2);
         builder.append(id).append(": "); 
         builder.append(time).append(": ");
         builder.append(title).append("\n");
      }
      // 画面を表示する
      TextView text = (TextView) findViewById(R.id.text); 
      text.setText(builder);
   }
   

   
}
