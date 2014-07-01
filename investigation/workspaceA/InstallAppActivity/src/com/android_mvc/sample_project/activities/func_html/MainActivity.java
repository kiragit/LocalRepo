package com.android_mvc.sample_project.activities.func_html;

import com.android_mvc.framework.activities.base.BaseNormalActivity;
import com.android_mvc.framework.activities.base.BaseTabHostActivity;
import com.android_mvc.framework.ui.UIBuilder;
import com.android_mvc.sample_project.common.Util;
import com.android_mvc.sample_project.controller.MainController;
import com.android_mvc.framework.ui.menu.OptionMenuBuilder;
import com.android_mvc.framework.ui.menu.OptionMenuDescription;
import com.android_mvc.framework.ui.tab.TabDescription;
import com.android_mvc.framework.ui.tab.TabHostBuilder;
import com.android_mvc.framework.ui.view.MLinearLayout;
import com.android_mvc.framework.ui.view.MTextView;
import com.android_mvc.framework.ui.view.etc.BaseJSAPI;

		/**
		 * サンプルのDB登録アクティビティ。
		 * @author id:language_and_engineering
		 *
		 */
		public class MainActivity extends BaseTabHostActivity {

			@Override
			public void defineContentView() {
				
			       // タブの定義を記述する。
		        new TabHostBuilder(context)
		            .setChildActivities( MainController.getChildActivities(this) )
		            .add(
		                new TabDescription("Plan")
		                    .text("Plan")
		                    .noIcon()
		                ,

		                new TabDescription("Do")
		                    .text("Do")
		                    .noIcon()
		                ,
		                
		                new TabDescription("Check")
		                    .text("Check")
		                    .noIcon()
		                ,
		                new TabDescription("Action")
		                    .text("Action")
		                    .noIcon()


		            )
				.display();
			}
			
	}


