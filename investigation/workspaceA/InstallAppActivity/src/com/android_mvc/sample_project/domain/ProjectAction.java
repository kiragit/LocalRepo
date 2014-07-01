package com.android_mvc.sample_project.domain;

import android.app.Activity;

import com.android_mvc.framework.controller.action.ActionResult;
import com.android_mvc.framework.controller.action.BaseAction;
import com.android_mvc.framework.controller.validation.ActivityParams;
import com.android_mvc.framework.ui.UIUtil;
import com.android_mvc.sample_project.activities.func_html.PlanActivity;
import com.android_mvc.sample_project.activities.func_html.PlanProjectActivity;
import com.android_mvc.sample_project.db.dao.ProjectDAO;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;

public class ProjectAction extends BaseAction {

	   private PlanProjectActivity activity;
	   private PROJECT p = new PROJECT();
	   private String CODE = p.getPROJECT_CODE();
	   private String NAME = p.getPROJECT_NAME();
	   private String COLOR = p.getPROJECT_COLOR();
	   private String STATE = p.getPROJECT_STATE();
		
	    public ProjectAction(PlanProjectActivity activity2) {
	        this.activity = activity2;
	    }
	    
	@Override
	public ActionResult exec() {
		int code = 0;
		
		ActivityParams params = activity.toParams();
		PROJECT p = new ProjectDAO(activity).findlatest();
	    if(p != null) code = p.getProject_code() + 1;
	    

        // 登録用の値を取得（バリデ通過済み）
 
        String name = (String)params.getValue(this.NAME);
        int color = Integer.valueOf((String)params.getValue(this.COLOR));
        int state = Integer.valueOf("0");


        // DB登録（すでに非同期でラップされているので，DB操作も同期的でよい）
        PROJECT project = new ProjectDAO(activity).create( code ,name, color,state );


        // 実行結果を返す
        return new PROJECTEditActionResult()
            .setRouteId("success")
            .add("new_Project_name", project.getProject_name())
            .add("new_friend_obj", project)
        ;
	}
	
    // 実行結果オブジェクト
    static class PROJECTEditActionResult extends ActionResult
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void onNextActivityStarted(final Activity activity)
        {
            UIUtil.longToast(activity, get("new_Project_name") + "を登録しました。");
        }
    }
    // NOTE: この内部クラスは，execメソッド中で匿名クラスとして定義することができない。
    // staticな内部クラスとして実装する必要がある。
    // 理由は，JavaのインナークラスとSerializableの仕様のため。
    // @see http://d.hatena.ne.jp/language_and_engineering/20120313/p1

}
