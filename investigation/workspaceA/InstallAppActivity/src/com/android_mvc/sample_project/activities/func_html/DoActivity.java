package com.android_mvc.sample_project.activities.func_html;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

import com.android_mvc.framework.activities.base.BaseNormalActivity;
import com.android_mvc.framework.controller.validation.ActivityParams;
import com.android_mvc.framework.ui.UIBuilder;
import com.android_mvc.framework.ui.view.MButton;
import com.android_mvc.framework.ui.view.MLinearLayout;
import com.android_mvc.framework.ui.view.MTextView;
import com.android_mvc.sample_project.common.Util;
import com.android_mvc.sample_project.controller.MainController;
import com.android_mvc.sample_project.db.dao.TaskDAO;
import com.android_mvc.sample_project.db.entity.lib.TASK;
import com.android_mvc.sample_project.db.entity.lib.TRAN;

public class DoActivity extends BaseNormalActivity {
	
	ArrayList<String> TOP5;
	MLinearLayout layout1;
	MTextView tv1;
	MTextView tv2;
	String button_type;
	int task_code=0;
	List<TASK> tasks;

    @Override
    public boolean requireProcBeforeUI(){
        Util.d("UI構築前に実行される処理です。");
        // UI構築前に処理を要求する
        return true;

    }
	
	// UI構築前に別スレッドで実行される処理
	 @Override
	 public void procAsyncBeforeUI(){
		Util.d("UI構築前に実行される処理です。");

        // 全タスクをDBからロード
        tasks = new TaskDAO(this).findAll();
        Util.d("タスクは " + tasks.size() +"件です。");
	 }
	 
	@Override
	public void defineContentView() {
		final DoActivity activity = this;
        // まず親レイアウトを定義
        new UIBuilder(context)
        .add(
            layout1 = new MLinearLayout(context)
              .orientationVertical()
              .widthFillParent()
              .heightWrapContent()
              .add(

                tv1 = new MTextView(context)
                  .text("ここにDBの中身が列挙されます。" )
                  .widthWrapContent()
                  .paddingPx(10)
                ,

                tv2 = new MTextView(context)
                  .invisible()
                  .textColor(Color.RED)
                  .widthWrapContent()
                  .paddingPx(10)

              )
        )
        .display();
        
        for( final TASK task : tasks )
        {
            // ※↑TASKをfinal宣言してるのは，Clickイベントの中から参照する事が目的

            layout1.add(
                // 水平方向のレイアウトを１個追加
                new MLinearLayout(context)
	                .orientationHorizontal()
	                .widthFillParent()
	                .heightWrapContent()
	                .paddingPx(10)
                    .add(
                        new MTextView(context)
                            .text(task.getTask_name()) // このタスクの説明を取得
                            .widthWrapContent()
                         ,
                         new MButton(context)
                         .text("開始")
                         .click(new OnClickListener(){
                               @Override
                               public void onClick(View v) {
                                   // DB更新へ
                            	   button_type="START";
                            	   task_code = task.getTask_code();
                                   MainController.submit(activity, button_type, task.getId());
                               }

                         	})
                          ,
                          new MButton(context)
                          .text("停止")
                          .click(new OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    // DB更新へ
                                	button_type="STOP";
                                	task_code = task.getTask_code();
                                    MainController.submit(activity, button_type, task.getId());
                                }

                          	})
                           ,
                          new MButton(context)
	                         .text("完了")
	                         .click(new OnClickListener(){
                               @Override
                               public void onClick(View v) {
                            	   button_type="DONE";
                            	   task_code = task.getTask_code();
                                   MainController.submit(activity, button_type, task.getId());
                               }

                         })
                     )
            );
        }

        // 描画
        layout1.inflateInside();

	}
	
	public ActivityParams toParams(){
		TRAN t = new TRAN();
		Date d = new Date();
		
		return new ActivityParams()
		.add("BUTTON_TYPE", "BUTTON_TYPE", button_type)
		.add(t.getTASK_CODE(), t.getTASK_CODE(), String.valueOf(task_code))
		.add(t.getSTART_YEAR(), t.getSTART_YEAR(), String.valueOf(d.getYear()))
		.add(t.getSTART_MONTH(), t.getSTART_MONTH(), String.valueOf(d.getMonth()))
		.add(t.getSTART_DAY(), t.getSTART_DAY(), String.valueOf(d.getDay()))
		.add(t.getSTART_HOUR(), t.getSTART_HOUR(),String.valueOf(d.getHours()))
		.add(t.getSTART_MINUTE(), t.getSTART_MINUTE(), String.valueOf(d.getMinutes()))
		.add(t.getSTART_SECOND(),t.getSTART_SECOND(),String.valueOf(d.getSeconds()))
				
		.add(t.getEND_YEAR(), t.getEND_YEAR(), d.getYear())
		.add(t.getEND_MONTH(), t.getEND_MONTH(), d.getMonth())
		.add(t.getEND_DAY(), t.getEND_DAY(), d.getDay())
		.add(t.getEND_HOUR(), t.getEND_HOUR(), d.getHours())
		.add(t.getEND_MINUTE(), t.getEND_MINUTE(), d.getMinutes())
		.add(t.getEND_SECOND(),t.getEND_SECOND(),d.getSeconds())
		
		;
	}
}
