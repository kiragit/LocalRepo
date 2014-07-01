package com.android_mvc.sample_project.db.dao;

import java.util.List;

import android.content.Context;

import com.android_mvc.framework.db.DBHelper;
import com.android_mvc.framework.db.dao.BaseDAO;
import com.android_mvc.framework.db.dao.Finder;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;
import com.android_mvc.sample_project.db.entity.lib.TASK;

public class TaskDAO extends BaseDAO<TASK> {

	public TaskDAO(Context context) {
		helper = new DBHelper(context);
	}

	// ------------ C --------------

	/**
	 * 新規タスクを保存。
	 */
	public TASK create(int code, int p_code, String name, int type, int color,int state ) {
		// 論理エンティティを構築
		TASK t = new TASK();
		t.setTask_code(code);
		t.setProject_code(p_code);
		t.setTask_name(name);
		t.setTask_type(type);
		t.setTask_color(color);
		t.setTask_state(state);

		// DB登録
		t.save(helper);

		return t;
	}

	// ------------ R --------------

	/**
	 * タスクを全て新しい順に返す。
	 */
	public List<TASK> findAll() {
		return findAll(helper, TASK.class);
	}

	/**
	 * 特定のIDのタスクを１人返す。
	 */
	public TASK findById(Long TASK_id) {
		return findById(helper, TASK.class, TASK_id);
	}

	public TASK findlatest() {

        List<TASK> records =  new Finder<TASK>(helper)
                .where("id > 0")
                .orderBy("id DESC")
                .offset(1)
                .limit(1)
                .findAll(TASK.class)
            ;
        if( records.size() > 0 )
        {
            return records.get(0);
        }
        else{
        	return null;
        }
	}
	// NOTE: 細かい条件で検索したい場合は，Finderを利用すること。
	// findAllやfindByIdの実装を参照。

	// ------------ U --------------

	// ------------ D --------------

	/**
	 * 特定のIDのタスクを削除。
	 */
	public void deleteById(Long TASK_id) {
		TASK f = findById(TASK_id);

		// DBからの削除を実行
		f.delete(helper);
	}

}
