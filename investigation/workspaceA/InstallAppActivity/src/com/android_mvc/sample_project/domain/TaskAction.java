package com.android_mvc.sample_project.domain;

import android.app.Activity;

import com.android_mvc.framework.controller.action.ActionResult;
import com.android_mvc.framework.controller.action.BaseAction;
import com.android_mvc.framework.controller.validation.ActivityParams;
import com.android_mvc.framework.ui.UIUtil;
import com.android_mvc.sample_project.activities.func_html.PlanActivity;
import com.android_mvc.sample_project.db.dao.ProjectDAO;
import com.android_mvc.sample_project.db.dao.TaskDAO;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;
import com.android_mvc.sample_project.db.entity.lib.TASK;

public class TaskAction extends BaseAction {

	   private PlanActivity activity;
	   private TASK task = new TASK();
	   private String CODE = task.getTASK_CODE();
	   private String P_CODE = task.getPROJECT_CODE();
	   private String NAME = task.getTASK_NAME();
	   private String TYPE = task.getTASK_TYPE();
	   private String COLOR = task.getTASK_COLOR();
	   private String STATE = task.getTASK_STATE();
		
	    public TaskAction(PlanActivity activity) {
	        this.activity = activity;
	    }
	    
	@Override
	public ActionResult exec() {
		
		int code = 0;
		int p_code = 0;
		ActivityParams params = activity.toParams();

		TASK task = new TaskDAO(activity).findlatest();
	    if(task != null) code = task.getProject_code() + 1;
	    
	    PROJECT p = new ProjectDAO(activity).findByName((String)params.getValue(this.P_CODE));
	    
        // 登録用の値を取得（バリデ通過済み）
        if(p != null)p_code = p.getProject_code(); 
        String name = (String)params.getValue(this.NAME);
        int type = Integer.valueOf((String)params.getValue(this.TYPE));
        int color = Integer.valueOf((String)params.getValue(this.COLOR));
        int state =0;


        // DB登録（すでに非同期でラップされているので，DB操作も同期的でよい）
        TASK t = new TaskDAO(activity).create( code , p_code, name, type, color, state );


        // 実行結果を返す
        return new TaskEditActionResult()
            .setRouteId("success")
            .add("new_task_name", t.getTask_name())
            .add("new_friend_obj", t)
        ;
	}
	
    // 実行結果オブジェクト
    static class TaskEditActionResult extends ActionResult
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void onNextActivityStarted(final Activity activity)
        {
            UIUtil.longToast(activity, get("new_task_name") + "を登録しました。");
        }
    }
    // NOTE: この内部クラスは，execメソッド中で匿名クラスとして定義することができない。
    // staticな内部クラスとして実装する必要がある。
    // 理由は，JavaのインナークラスとSerializableの仕様のため。
    // @see http://d.hatena.ne.jp/language_and_engineering/20120313/p1


}
