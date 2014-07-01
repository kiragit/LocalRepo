package com.android_mvc.sample_project.db.dao;

import java.util.List;

import android.content.Context;

import com.android_mvc.framework.db.DBHelper;
import com.android_mvc.framework.db.dao.BaseDAO;
import com.android_mvc.sample_project.db.entity.lib.TRAN;

public class TranDAO extends BaseDAO<TRAN> {

	public TranDAO(Context context) {
		helper = new DBHelper(context);
	}

	// ------------ C --------------

	/**
	 * 新規プロジェクトを保存。
	 */
	public TRAN create(int code, int sYear, int sMonth, int sDay, int sHour, int sMin, int sSec,int eYear, int eMonth, int eDay, int eHour, int eMin, int eSec ) {
		// 論理エンティティを構築
		TRAN t = new TRAN();
		t.setTask_code(code);
		t.setStart_year(sYear);
		t.setStart_month(sMonth);
		t.setStart_day(sDay);
		t.setStart_hour(sHour);
		t.setStart_minute(sMin);
		t.setStart_second(sSec);
		t.setEnd_year(eYear);
		t.setEnd_month(eMonth);
		t.setEnd_day(eDay);
		t.setEnd_hour(eHour);
		t.setEnd_minute(eMin);
		t.setEnd_second(eSec);
		
		// DB登録
		t.save(helper);

		return t;
	}

	// ------------ R --------------

	/**
	 * プロジェクトを全て新しい順に返す。
	 */
	public List<TRAN> findAll() {
		return findAll(helper, TRAN.class);
	}

	/**
	 * 特定のIDのプロジェクトを１人返す。
	 */
	public TRAN findById(Long Task_id) {
		return findById(helper, TRAN.class, Task_id);
	}

	// NOTE: 細かい条件で検索したい場合は，Finderを利用すること。
	// findAllやfindByIdの実装を参照。

	// ------------ U --------------

	// ------------ D --------------

	/**
	 * 特定のIDのを削除。
	 */
	public void deleteById(Long Tran_id) {
		TRAN t = findById(Tran_id);

		// DBからの削除を実行
		t.delete(helper);
	}
}
