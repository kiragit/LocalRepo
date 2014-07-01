package com.android_mvc.sample_project.activities.func_html;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import com.android_mvc.framework.activities.base.BaseNormalActivity;
import com.android_mvc.framework.controller.validation.ActivityParams;
import com.android_mvc.framework.ui.UIBuilder;
import com.android_mvc.framework.ui.view.MButton;
import com.android_mvc.framework.ui.view.MEditText;
import com.android_mvc.framework.ui.view.MLinearLayout;
import com.android_mvc.framework.ui.view.MTextView;
import com.android_mvc.sample_project.common.Util;
import com.android_mvc.sample_project.controller.MainController;
import com.android_mvc.sample_project.db.dao.ProjectDAO;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;

public class PlanProjectActivity extends BaseNormalActivity {

	MLinearLayout PROJECT_NAME_Layout;
	MTextView PROJECT_NAME_Lavel;
	MEditText PROJECT_NAME_Text;
	String ProjectNameLavel = "プロジェクト名";

	MLinearLayout PROJECT_COLOR_Layout;
	MTextView PROJECT_COLOR_Lavel;
	MEditText PROJECT_COLOR_Text;
	String ProjectColorLavel = "色";
	
	MLinearLayout PROJECT_List_Layout;
	
	List<PROJECT> projects;
	
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

       // 全プロジェクトをDBからロード
       projects = new ProjectDAO(this).findAll();
       Util.d("プロジェクトは " + projects.size() +"件です。");
	 }
	 
	@Override
	public void defineContentView() {

		final PlanProjectActivity activity = this;

		new UIBuilder(context)
		.add(
			PROJECT_NAME_Layout = new MLinearLayout(context)
			.orientationHorizontal()
			.widthFillParent()
			.add(
				PROJECT_NAME_Lavel = new MTextView(context).text(ProjectNameLavel).widthWrapContent()
			,	PROJECT_NAME_Text = new MEditText(context).widthPx(200)
			)
		,
			PROJECT_COLOR_Layout = new MLinearLayout(context)
			.orientationHorizontal()
			.widthFillParent()
			.add(
				PROJECT_COLOR_Lavel = new MTextView(context).text(ProjectColorLavel).widthWrapContent()
			,	PROJECT_COLOR_Text= new MEditText(context).widthPx(200)
			)
		,
			PROJECT_List_Layout = new MLinearLayout(context)
            .orientationVertical()
            .widthFillParent()
            .heightWrapContent()
            .add(
	    			new MButton(context)
	    	          .text("DB登録")
	    	          .click(new OnClickListener(){
	    	              @Override
	    	              public void onClick(View v) {
	    	                  MainController.submit(activity);
	    	              }
	    	
	    	          })
	    	          ,
	              // 戻るボタン
	             new MButton(context)
	             	.text("戻る")
	                .click(new OnClickListener(){
	                	@Override
	                	public void onClick(View v) {
	                		MainController.submit(activity, "BACK_TO_PLAN");
	                    }
	                })
	                )
		).display();
		
        for( final PROJECT p : projects )
        {
            // ※↑TASKをfinal宣言してるのは，Clickイベントの中から参照する事が目的
                         
        	PROJECT_List_Layout.add(
                    // 水平方向のレイアウトを１個追加
                    new MLinearLayout(context)
                        .orientationHorizontal()
                        .widthFillParent()
                        .heightWrapContent()
                        .paddingPx(10)
                        .add(

                            new MTextView(context)
                                .text( p.getProject_name() + ":" + p.getProject_color() ) // この友達の説明を取得
                                .widthWrapContent()
                            ,
                            new MButton(context)
                                .text("更新")
                                .click(new OnClickListener(){

                                      @Override
                                      public void onClick(View v) {
                                          // DB更新へ
                                          MainController.submit(activity, "UPDATE_FAVORITE_FLAG", p.getId());
                                      }

                                })
                            ,

                            new MButton(context)
                                .text("削除")
                                .click(new OnClickListener(){

                                      @Override
                                      public void onClick(View v) {
                                          // DBから削除へ
                                          MainController.submit(activity, "DELETE_FRIEND", p.getId());
                                      }

                                })
                        )
                );
        }
        
        // 描画
        PROJECT_List_Layout.inflateInside();
	}

	public ActivityParams toParams(){
		return new ActivityParams()
		.add(ProjectNameLavel, "PROJECT_NAME", PROJECT_NAME_Text.text())
		.add(ProjectColorLavel, "PROJECT_COLOR", PROJECT_COLOR_Text.text())
		;
	}
}
