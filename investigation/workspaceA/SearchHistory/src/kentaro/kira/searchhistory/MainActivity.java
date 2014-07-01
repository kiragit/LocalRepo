package kentaro.kira.searchhistory;

import java.util.Map;

import kentaro.kira.searchhistory.R.id;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity  implements OnClickListener{

   	Intent intent;
   	EditText EditKeyword;
   	String keyword;
   	ArrayAdapter<String> adapter;
   	ListView listView;
   	SharedPreferences pref;
   	AlertDialog.Builder aDlg;
   	InputMethodManager IMM;
   	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //プリファレンスの取得
        pref = this.getSharedPreferences("keyowrds",MODE_PRIVATE);
        //ソフトウェアキーボードの制御
        IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //追加ボタン
    	Button BTNsearch =  (Button) findViewById(id.BTNadd);
        BTNsearch.setOnClickListener(this);
        
    	//ListViewオブジェクトの取得
        listView=(ListView)findViewById(R.id.list_view);
        //ArrayAdapterオブジェクト生成
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //Adapterのセット
        listView.setAdapter(adapter);
        //list初期化
        initListView();
        //削除時の確認ダイアログ
        aDlg = new AlertDialog.Builder(this);
		aDlg.setTitle("確認");
		aDlg.setMessage("本当に削除しますか？");
		aDlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				adapter.remove(keyword);
	            Editor edit = pref.edit();
	            edit.remove(keyword);
	            edit.commit();
			}
		});
		aDlg.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
        //リストアイテムのクリック処理
        listView.setOnItemClickListener(
        		new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						// TODO 自動生成されたメソッド・スタブ
						ListView list = (ListView)parent;
						String keyword = list.getItemAtPosition(position).toString();
				    	intent = new Intent(Intent.ACTION_WEB_SEARCH);
			        	intent.putExtra(SearchManager.QUERY, keyword);
			        	startActivity(intent);
					}
		});
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View arg1, int position, long arg3) {
							ListView list = (ListView)parent;
							keyword = list.getItemAtPosition(position).toString();
							aDlg.create().show();
							
						return false;
					}
		});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //インターフェイスを実装 implements OnClickListener
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.BTNadd:
        	EditKeyword = (EditText) findViewById(id.keyword);
            //EditText(テキスト)を取得し、アダプタに追加
            adapter.add(EditKeyword.getText().toString());
            Editor edit = pref.edit();
            edit.putString(EditKeyword.getText().toString(), "");
            edit.commit();
            EditKeyword.setText("");
            IMM.hideSoftInputFromWindow(EditKeyword.getWindowToken(), 0);
        } 
    }    
    //リストの初期化（プリファレンスより保存しているキーワード取得、設定）
    private void initListView(){
    	Map<String, String> map = (Map<String, String>) pref.getAll();
    	for(String s:map.keySet()){
    		 adapter.add(s);
    	}
    }

}
