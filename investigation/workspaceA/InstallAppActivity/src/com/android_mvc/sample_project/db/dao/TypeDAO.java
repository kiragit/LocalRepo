package com.android_mvc.sample_project.db.dao;

import java.util.List;

import android.content.Context;

import com.android_mvc.framework.db.DBHelper;
import com.android_mvc.framework.db.dao.BaseDAO;
import com.android_mvc.sample_project.db.entity.lib.TYPE;

public class TypeDAO extends BaseDAO<TYPE> {

	public TypeDAO(Context context) {
		helper = new DBHelper(context);
	}

	// ------------ C --------------

	/**
	 * 新規プロジェクトを保存。
	 */
	public TYPE create(int code, String name, String flavor ) {
		// 論理エンティティを構築
		TYPE t = new TYPE();
		t.setType_code(code);
		t.setType_name(name);
		t.setType_flavor(flavor);
		
		// DB登録
		t.save(helper);

		return t;
	}

	// ------------ R --------------

	/**
	 * プロジェクトを全て新しい順に返す。
	 */
	public List<TYPE> findAll() {
		return findAll(helper, TYPE.class);
	}

	/**
	 * 特定のIDのプロジェクトを１人返す。
	 */
	public TYPE findById(Long Task_id) {
		return findById(helper, TYPE.class, Task_id);
	}

	// NOTE: 細かい条件で検索したい場合は，Finderを利用すること。
	// findAllやfindByIdの実装を参照。

	// ------------ U --------------

	// ------------ D --------------

	/**
	 * 特定のIDのを削除。
	 */
	public void deleteById(Long Type_id) {
		TYPE t = findById(Type_id);

		// DBからの削除を実行
		t.delete(helper);
	}
}
