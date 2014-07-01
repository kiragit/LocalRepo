package com.gmail.kira.kentaro;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class GoogleMapSampleActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "Sudoku";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG","onCreate");
        setContentView(R.layout.main);

        //ボタンがクリックされた際のイベントリスナーを定義
        //本アプリの説明画面ボタン
        View aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
       
        //Game開始画面ボタン
        View newGameButton = this.findViewById(R.id.new_button);
        newGameButton.setOnClickListener(this);
        
        //終了ボタン
        View exitButton = this.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){
		case R.id.about_button:
			Log.d(TAG, "Click About Button");
			Intent i = new Intent(this,About.class);
			startActivity(i);
			break;
		case R.id.new_button:
			Log.d(TAG, "Click new Button");
			openNewGameDialog();
			break;
		case R.id.exit_button:
			Log.d(TAG, "Click Exit Button");
			finish();
			break;
		}

	}

	private void openNewGameDialog() {
		new AlertDialog.Builder(this)
		.setTitle(R.string.new_game_title)
		.setItems(R.array.difficulty, 
				new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						startGame(which);
					}
				})
			.show();
	}
	
	private void startGame(int which){
		Log.d(TAG, "clicked on "+which+"by select Game Level");
		/** 選択されたレベルでゲームを開始する。**/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.settings:
			startActivity(new Intent(this,Settings.class));
		return true;
		}
		return false;
	}
}