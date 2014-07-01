package com.android_mvc.sample_project.controller;

import com.android_mvc.framework.controller.BaseController;
import com.android_mvc.framework.controller.ControlFlowDetail;
import com.android_mvc.framework.controller.action.ActionResult;
import com.android_mvc.framework.controller.action.BLExecutor;
import com.android_mvc.framework.controller.routing.Router;
import com.android_mvc.framework.controller.routing.RoutingTable;
import com.android_mvc.framework.controller.routing.TabContentMapping;
import com.android_mvc.framework.controller.validation.ValidationExecutor;
import com.android_mvc.framework.controller.validation.ValidationResult;
import com.android_mvc.sample_project.activities.func_html.ActionActivity;
import com.android_mvc.sample_project.activities.func_html.CheckActivity;
import com.android_mvc.sample_project.activities.func_html.PlanActivity;
import com.android_mvc.sample_project.activities.func_html.MainActivity;
import com.android_mvc.sample_project.activities.func_html.DoActivity;
import com.android_mvc.sample_project.activities.func_html.PlanProjectActivity;
import com.android_mvc.sample_project.activities.installation.InstallAppActivity;
import com.android_mvc.sample_project.activities.installation.InstallCompletedActivity;
import com.android_mvc.sample_project.controller.DBValidation;
import com.android_mvc.sample_project.domain.ProjectAction;
import com.android_mvc.sample_project.domain.TaskAction;
import com.android_mvc.sample_project.domain.TranAction;

public class MainController extends BaseController{

	public static void submit(InstallAppActivity installAppActivity, boolean installExecutedFlag){
	    // インストールをスキップしたかどうか
	    if( installExecutedFlag )
	    {
	        // インストール完了画面へ
	        Router.go(installAppActivity, InstallCompletedActivity.class);
	    }
	    else
	    {
	        // トップ画面へ
	        Router.go(installAppActivity, MainActivity.class);
	    }
	}
	
	
	/**
	 * インストール完了画面からの遷移時
	 */
	public static void submit(InstallCompletedActivity activity) {
	    // トップ画面へ
	    Router.go(activity, MainActivity.class);
	}

    /**
     * Plan画面からの遷移時
     */
    public static void submit(PlanActivity activity, String button_type) {

        // 送られてきたボタンタイプに応じて，遷移先を振り分ける。

        // extra付きの遷移を実行
        if( "PROJECT".equals(button_type) )
        {
            Router.go(activity, PlanProjectActivity.class);
        }
    }
    
    /**
     * Plan画面からの遷移時
     */
    public static void submit(PlanProjectActivity activity, String button_type) {

        // 送られてきたボタンタイプに応じて，遷移先を振り分ける。

        // extra付きの遷移を実行
        if( "BACK_TO_PLAN".equals(button_type) )
        {
            Router.go(activity, MainActivity.class);
        }
    }
        
	//Plan PROJECT
	public static void submit(final PlanProjectActivity activity) {
		new ControlFlowDetail<PlanProjectActivity>(activity)
		
		//画面の入力値チェック
		.setValidation(new ValidationExecutor(){

			@Override
			//バリデーション処理
			public ValidationResult doValidate() {
				return new DBValidation().validate(activity);
			}

			@Override
			//バリデーションでエラー発見時
			public void onValidationFailed() {
				showErrMessages();
                stayInThisPage();	
			}
			
		})
		.setBL(new BLExecutor(){

			@Override
			public ActionResult doAction() {
				return new ProjectAction(activity).exec();
			}
			
		})
		.onBLExecuted(
                // BL実行後の遷移先の一覧
                //new RoutingTable().map("success", MainActivity.class )
				STAY_THIS_PAGE_ALWAYS	
            )
            .setDialogText("お待ちください")
            .startControl();
		
	}
	
	//Plan Task
	public static void submit(final PlanActivity activity) {
		new ControlFlowDetail<PlanActivity>(activity)
		
		//画面の入力値チェック
		.setValidation(new ValidationExecutor(){

			@Override
			//バリデーション処理
			public ValidationResult doValidate() {
				return new DBValidation().validate(activity);
			}

			@Override
			//バリデーションでエラー発見時
			public void onValidationFailed() {
				showErrMessages();
                stayInThisPage();	
			}
			
		})
		.setBL(new BLExecutor(){

			@Override
			public ActionResult doAction() {
				return new TaskAction(activity).exec();
			}
			
		})
		.onBLExecuted(
                // BL実行後の遷移先の一覧
                new RoutingTable().map("success", MainActivity.class )
            )
            .setDialogText("お待ちください")
            .startControl();
		
	}
	
    /**
     * タブ親サンプル画面から呼び出される子画面のリスト
     */
    public static TabContentMapping getChildActivities(MainActivity activity)
    {
        // タブのタグ文字列に対応するアクティビティを指定する。
        return new TabContentMapping()
            .add( "Plan", PlanActivity.class )
            .add( "Do", DoActivity.class )
            .add( "Check", CheckActivity.class )
            .add( "Action", ActionActivity.class )
        ;
    }


	public static void submit(PlanProjectActivity activity, String string,
			Long id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}


	public static void submit(final DoActivity activity, String button_type, Long id) {
		
		// extra付きの遷移を実行
        if( "START".equals(button_type) )
        {
			new ControlFlowDetail<DoActivity>(activity)
			.setBL(new BLExecutor(){
	
				@Override
				public ActionResult doAction() {
					return new TranAction(activity).exec();
				}
				
			})
			.onBLExecuted(
	                // BL実行後の遷移先の一覧
	                //new RoutingTable().map("success", MainActivity.class )
					STAY_THIS_PAGE_ALWAYS	
	            )
	            .setDialogText("お待ちください")
	            .startControl();
		
	}		
	}

}
