package com.kira.kentaro.housemovingevidencephotographs;

import com.kira.kentaro.db.DBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* DB作成 */

		// データベースヘルパーのインスタンスを作成する（まだデータベースはできない）
		DBHelper dbHelper = new DBHelper(this);
		// データベースオブジェクトを取得する（データベースにアクセスすると作成される。）
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// データベースを閉じる
		db.close();

		/* 画面作成 */

		// button
		TextView btn = (TextView) findViewById(R.id.other);
		btn.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// implements OnClickListener
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this, TakePhoto.class);
		startActivity(intent);
	}

}
