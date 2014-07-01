package com.android_mvc.sample_project.db.dao;

import java.util.List;

import android.content.Context;

import com.android_mvc.framework.db.DBHelper;
import com.android_mvc.framework.db.dao.BaseDAO;
import com.android_mvc.sample_project.db.entity.lib.COLOR;

public class ColorDAO extends BaseDAO<COLOR> {

	public ColorDAO(Context context) {
		helper = new DBHelper(context);
	}

	// ------------ C --------------

	/**
	 * 新規プロジェクトを保存。
	 */
	public COLOR create(int code, String name, String flavor ) {
		// 論理エンティティを構築
		COLOR t = new COLOR();
		t.setColor_code(code);
		t.setColor_name(name);
		t.setColor_flavor(flavor);
		
		// DB登録
		t.save(helper);

		return t;
	}

	// ------------ R --------------

	/**
	 * プロジェクトを全て新しい順に返す。
	 */
	public List<COLOR> findAll() {
		return findAll(helper, COLOR.class);
	}

	/**
	 * 特定のIDのプロジェクトを１人返す。
	 */
	public COLOR findById(Long Task_id) {
		return findById(helper, COLOR.class, Task_id);
	}

	// NOTE: 細かい条件で検索したい場合は，Finderを利用すること。
	// findAllやfindByIdの実装を参照。

	// ------------ U --------------

	// ------------ D --------------

	/**
	 * 特定のIDのを削除。
	 */
	public void deleteById(Long Color_id) {
		COLOR t = findById(Color_id);

		// DBからの削除を実行
		t.delete(helper);
	}
}
