package com.android_mvc.sample_project.db.dao;

import java.util.List;

import android.content.Context;

import com.android_mvc.framework.db.DBHelper;
import com.android_mvc.framework.db.dao.BaseDAO;
import com.android_mvc.framework.db.dao.Finder;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;

public class ProjectDAO extends BaseDAO<PROJECT> {

	public ProjectDAO(Context context) {
		helper = new DBHelper(context);
	}

	// ------------ C --------------

	/**
	 * 新規プロジェクトを保存。
	 */
	public PROJECT create(int code, String name, int color ,int state) {
		// 論理エンティティを構築
		PROJECT p = new PROJECT();
		p.setProject_code(code);
		p.setProject_name(name);
		p.setProject_color(color);
		p.setProject_state(state);

		// DB登録
		p.save(helper);

		return p;
	}

	// ------------ R --------------

	/**
	 * プロジェクトを全て新しい順に返す。
	 */
	public List<PROJECT> findAll() {
		return findAll(helper, PROJECT.class);
	}

	/**
	 * 特定のIDのプロジェクトを１人返す。
	 */
	public PROJECT findById(Long PROJECT_id) {
		return findById(helper, PROJECT.class, PROJECT_id);
	}

	public PROJECT findlatest(){
        List<PROJECT> records =  new Finder<PROJECT>(helper)
                .where("id > 0")
                .orderBy("id DESC")
                .offset(1)
                .limit(1)
                .findAll(PROJECT.class)
            ;
        if( records.size() > 0 )
        {
            return records.get(0);
        }
        else{
        	return null;
        }
	}
	public PROJECT findByName(String name){
		PROJECT p = new PROJECT();
        List<PROJECT> records =  new Finder<PROJECT>(helper)
                .where(p.getPROJECT_NAME() + " = " + name)
                .orderBy("id DESC")
                .offset(1)
                .limit(1)
                .findAll(PROJECT.class)
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
	 * 特定のIDのを削除。
	 */
	public void deleteByName(String name) {
		PROJECT p = new PROJECT();
		
        List<PROJECT> records =  new Finder<PROJECT>(helper)
                .where("id > 0")
                .where(p.getPROJECT_NAME() + " = " + name)
                .orderBy("id DESC")
                .offset(1)
                .limit(1)
                .findAll(PROJECT.class)
            ;
            if( records.size() > 0 )
            {
                records.get(0).delete(helper);
            }
            else
            {
                
            }

	}
}
