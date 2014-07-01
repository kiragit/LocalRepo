package com.kira.kentaro.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	/* データベース名 */
	private final static String DB_NAME = "androidstudydb";
	/* データベースのバージョン */
	private final static int DB_VER = 1;
	/* コンテキスト */
	private Context mContext;
	/* デフォルト文字コード */
	private final static String charcode = "UTF-8";
	/* システム固有改行コード */
	private final static String lineSeparator = System
			.getProperty("line.separator");
	/* システム固有ファイル区切り文字 */
	private final static String fileSeparator = System
			.getProperty("file.separator");
	/* DDL内のSQL文の分割文字列 */
	private final static String separate = "/";
	/*assets内、DDL(create)の格納フォルダパス*/
	private final static String DDL_CREATE = "DDL"+fileSeparator+"CREATE";
	/*assets内、DDL(create)の格納フォルダパス*/
	private final static String DDL_DATA = "DDL"+fileSeparator+"DATA";
	/*assets内、DDL(drop)の格納フォルダパス*/
	private final static String DDL_DROP= "DDL"+fileSeparator+"DROP";
	
	

	/**
	 * コンストラクタ
	 */
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
		mContext = context;
	}

	/**
	 * データベースが作成された時に呼ばれます。 
	 * テーブルを作成します。assets/DDL/CREATE内に定義されているsqlを実行します。
	 * 初期データを投入します。assets/DDL/DATA内に定義されているsqlを実行します。
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			execSql(db, DDL_CREATE);
			execSql(db, DDL_DATA);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * データベースをバージョンアップした時に呼ばれます。 assets/sql/drop内に定義されているsqlを実行します。
	 * その後onCreate()メソッドを呼び出します。
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			execSql(db, DDL_DROP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		onCreate(db);
	}

	/**
	 * 引数に指定したassetsフォルダ内のsqlを実行します。
	 * 
	 * @param db
	 *            データベース
	 * @param assetsDir
	 *            assetsフォルダ内のフォルダのパス
	 * @throws IOException
	 */
	private void execSql(SQLiteDatabase db, String assetsDir)
			throws IOException {
		AssetManager as = mContext.getResources().getAssets();
		try {
			String files[] = as.list(assetsDir);
			for (int i = 0; i < files.length; i++) {
				String str = readFile(as.open(assetsDir + fileSeparator
						+ files[i]));
				for (String sql : str.split(separate)) {
					db.execSQL(sql);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ファイルから文字列を読み込みます。
	 * 
	 * @param is
	 * @return ファイルの文字列
	 * @throws IOException
	 */
	private String readFile(InputStream is) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, charcode));

			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str + lineSeparator);
			}
			return sb.toString();
		} finally {
			if (br != null)
				br.close();
		}
	}

}
