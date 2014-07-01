package com.android_mvc.sample_project.activities.func_html;

import android.view.View;
import android.view.View.OnClickListener;

import com.android_mvc.framework.activities.base.BaseNormalActivity;
import com.android_mvc.framework.controller.validation.ActivityParams;
import com.android_mvc.framework.ui.UIBuilder;
import com.android_mvc.framework.ui.view.MButton;
import com.android_mvc.framework.ui.view.MCheckBox;
import com.android_mvc.framework.ui.view.MEditText;
import com.android_mvc.framework.ui.view.MLinearLayout;
import com.android_mvc.framework.ui.view.MTextView;
import com.android_mvc.sample_project.controller.MainController;

public class PlanActivity extends BaseNormalActivity {

	MLinearLayout PROJECT_Layout;
	MTextView PROJECT_Lavel;
	MEditText PROJECT_Text;
	String TASKMeiLavel = "プロジェクト名";

	MLinearLayout TASK_NAME_Layout;
	MTextView TASK_NAME_Lavel;
	MEditText TASK_NAME_Text;
	String TaskMeiLavel = "タスク名";

	MLinearLayout TASK_COLOR_Layout;
	MTextView TASK_COLOR_Lavel;
	MEditText TASK_COLOR_Text;
	String TASKColorLavel = "色";
	
	MLinearLayout TASK_TYPE_Layout;
	MTextView TASK_TYPE_Lavel;
	MEditText TASK_TYPE_Text;
	String TaskTypeLavel = "タイプ";

	MButton TOUROKU;
	MButton PROJECT;

	@Override
	public void defineContentView() {

		final PlanActivity activity = this;

		new UIBuilder(context)
		.add(
			PROJECT_Layout = new MLinearLayout(context)
			.orientationHorizontal()
			.widthFillParent()
			.add(
				PROJECT_Lavel = new MTextView(context).text(TASKMeiLavel).widthWrapContent()
			,	PROJECT_Text = new MEditText(context).widthPx(100)
			,	PROJECT  = new MButton(context).text("新規").click(new OnClickListener(){
                	@Override
                	public void onClick(View v) {
                		MainController.submit(activity,"PROJECT");
                	}
            	})
			)
		,
			TASK_NAME_Layout = new MLinearLayout(context)
			.orientationHorizontal()
			.widthFillParent()
			.add(
				TASK_NAME_Lavel = new MTextView(context).text(TaskMeiLavel).widthWrapContent()
			,	TASK_NAME_Text = new MEditText(context).widthPx(200)
			)
		,
			TASK_COLOR_Layout = new MLinearLayout(context)
			.orientationHorizontal()
			.widthFillParent()
			.add(
				TASK_COLOR_Lavel = new MTextView(context).text(TASKColorLavel).widthWrapContent()
			,	TASK_COLOR_Text= new MEditText(context).widthPx(200)
			)
		,
			TASK_TYPE_Layout = new MLinearLayout(context)
			.orientationHorizontal()
			.widthFillParent()
			.add(
				TASK_TYPE_Lavel = new MTextView(context).text(TaskTypeLavel).widthWrapContent()
			,	TASK_TYPE_Text= new MEditText(context).widthPx(200)
			)
		,
			TOUROKU = new MButton(context)
              .text("この内容でDB登録")
              .click(new OnClickListener(){
                  @Override
                  public void onClick(View v) {
                      MainController.submit(activity);
                  }

              })
		).display();

	}

	public ActivityParams toParams(){
		
		return new ActivityParams()
		.add(TASKMeiLavel, "PROJECT_NAME", PROJECT_Text.text())
		.add(TaskMeiLavel, "TASK_NAME", TASK_NAME_Text.text())
		.add(TASKColorLavel, "TASK_COLOR", TASK_COLOR_Text.text())
		.add(TaskTypeLavel, "TASK_TYPE", TASK_TYPE_Text.text())
		;
	}
}
