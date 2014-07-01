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
import static org.example.events.Constants.CONTENT_URI;
import static org.example.events.Constants.TIME;
import static org.example.events.Constants.TITLE;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class Events extends ListActivity {
   private static String[] FROM = { _ID, TIME, TITLE, };
   private static int[] TO = { R.id.rowid, R.id.time, R.id.title, };
   private static String ORDER_BY = TIME + " DESC";

   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      addEvent("Hello, Android!");
      Cursor cursor = getEvents();
      showEvents(cursor);
   }
   

   
   private void addEvent(String string) {
      // Eventsデータソースに新しいレコードを挿入する。
      // 削除、更新も同様の方法で実行できる
      ContentValues values = new ContentValues();
      values.put(TIME, System.currentTimeMillis());
      values.put(TITLE, string);
      getContentResolver().insert(CONTENT_URI, values);
   }
   

   
   private Cursor getEvents() {
      // 管理されたクエリーを実行する。Activityは、クローズの他、
      // 必要な場合は再クエリーを処理する
      return managedQuery(CONTENT_URI, FROM, null, null, ORDER_BY);
   }
   

   private void showEvents(Cursor cursor) {
      // データバインドをセットアップする
      SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
            R.layout.item, cursor, FROM, TO);
      setListAdapter(adapter);
   }
}