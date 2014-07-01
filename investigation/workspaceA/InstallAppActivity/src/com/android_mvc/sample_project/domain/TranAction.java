package com.android_mvc.sample_project.domain;

import android.app.Activity;

import com.android_mvc.framework.controller.action.ActionResult;
import com.android_mvc.framework.controller.action.BaseAction;
import com.android_mvc.framework.controller.validation.ActivityParams;
import com.android_mvc.framework.ui.UIUtil;
import com.android_mvc.sample_project.activities.func_html.DoActivity;
import com.android_mvc.sample_project.activities.func_html.PlanActivity;
import com.android_mvc.sample_project.common.Util;
import com.android_mvc.sample_project.db.dao.TranDAO;
import com.android_mvc.sample_project.db.entity.lib.TRAN;

public class TranAction extends BaseAction {

	private DoActivity activity;
	private TRAN tran = new TRAN();
	private String CODE = tran.getTASK_CODE();
	private String START_YEAR = tran.getSTART_YEAR();
	private String START_MONTH = tran.getSTART_MONTH();
	private String START_DAY = tran.getSTART_DAY();
	private String START_HOUR = tran.getSTART_HOUR();
	private String START_MINUTE = tran.getSTART_MINUTE();
	private String START_SECOND = tran.getSTART_SECOND();
	private String END_YEAR = tran.getEND_YEAR();
	private String END_MONTH = tran.getEND_MONTH();
	private String END_DAY = tran.getEND_DAY();
	private String END_HOUR = tran.getEND_HOUR();
	private String END_MINUTE = tran.getEND_MINUTE();
	private String END_SECOND = tran.getEND_SECOND();

	private int code;
	private int start_year;
	private int start_month;
	private int start_day;
	private int start_hour;
	private int start_minute;
	private int start_second;
	private int end_year;
	private int end_month;
	private int end_day;
	private int end_hour;
	private int end_minute;
	private int end_second;

	public TranAction(DoActivity activity2) {
		this.activity = activity2;
	}

	@Override
	public ActionResult exec() {

		ActivityParams params = activity.toParams();

		// 登録用の値を取得（バリデ通過済み）
		String button_type = (String) params.getValue("BUTTON_TYPE");
		if (button_type == "START") {
			Util.d("---------------------");
			Util.d(this.START_YEAR);
			Util.d((String) params.getValue(this.START_YEAR));
			Util.d("---------------------");
			start_year = Integer.valueOf((String) params.getValue(this.START_YEAR));
			start_month = Integer.valueOf((String) params.getValue(this.START_MONTH));
			start_day = Integer.valueOf((String) params.getValue(this.START_DAY));
			start_hour = Integer.valueOf((String) params.getValue(this.START_HOUR));
			start_minute = Integer.valueOf((String) params.getValue(this.START_MINUTE));
			start_second = Integer.valueOf((String) params.getValue(this.START_SECOND));
			code = Integer.valueOf((String) params.getValue(this.CODE));
		} else if (button_type == "STOP") {
			end_year = Integer.valueOf((String) params.getValue(this.END_YEAR));
			end_month = Integer.valueOf((String) params.getValue(this.END_MONTH));
			end_day = Integer.valueOf((String) params.getValue(this.END_DAY));
			end_hour = Integer.valueOf((String) params.getValue(this.END_HOUR));
			end_minute = Integer.valueOf((String) params.getValue(this.END_MINUTE));
			end_second = Integer.valueOf((String) params.getValue(this.END_SECOND));
		}

		// DB登録（すでに非同期でラップされているので，DB操作も同期的でよい）
		TRAN t = new TranDAO(activity).create(code, start_year, start_month,
				start_day, start_hour, start_minute, start_second, end_year,
				end_month, end_day, end_hour, end_minute, end_second);

		// 実行結果を返す
		return new TaskEditActionResult().setRouteId("success");
	}

	// 実行結果オブジェクト
	static class TaskEditActionResult extends ActionResult {
		private static final long serialVersionUID = 1L;

		@Override
		public void onNextActivityStarted(final Activity activity) {
			UIUtil.longToast(activity, "タスクを登録しました。");
		}
	}
	// NOTE: この内部クラスは，execメソッド中で匿名クラスとして定義することができない。
	// staticな内部クラスとして実装する必要がある。
	// 理由は，JavaのインナークラスとSerializableの仕様のため。
	// @see http://d.hatena.ne.jp/language_and_engineering/20120313/p1

}
