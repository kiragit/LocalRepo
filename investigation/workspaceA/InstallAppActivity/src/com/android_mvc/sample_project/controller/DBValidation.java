package com.android_mvc.sample_project.controller;

import com.android_mvc.framework.controller.validation.ValidationResult;
import com.android_mvc.sample_project.activities.func_html.DoActivity;
import com.android_mvc.sample_project.activities.func_html.PlanActivity;
import com.android_mvc.sample_project.activities.func_html.PlanProjectActivity;
import com.android_mvc.sample_project.controller.util.ValidationsUtil;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;
import com.android_mvc.sample_project.db.entity.lib.TASK;
import com.android_mvc.sample_project.db.entity.lib.TRAN;

public class DBValidation extends ValidationsUtil {

	private TASK t = new TASK();
	private PROJECT p = new PROJECT();
	private TRAN tran = new TRAN();
	
	private String TASK_NAME = t.getTASK_NAME();
	private String TASK_COLOR = t.getTASK_COLOR();
	private String TASK_TYPE = t.getTASK_TYPE();
	private String TASK_CODE = t.getTASK_CODE();
	private String TASK_STATE = t.getTASK_STATE();
	
	private String PROJECT_CODE = p.getPROJECT_CODE();
	private String PROJECT_NAME = p.getPROJECT_NAME();
	private String PROJECT_COLOR = p.getPROJECT_COLOR();
	private String PROJECT_STATE = p.getPROJECT_STATE();
	
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
	
	 public ValidationResult validate(PlanActivity activity){
		 //VALID for TASK 
		initValidationOf(activity);
		assertNotEmpty(TASK_NAME);
		assertValidInteger(TASK_TYPE);
		return getValidationResult();
	}

	public ValidationResult validate(PlanProjectActivity activity) {
		 //VALID for PROJECT
		initValidationOf(activity);
		assertNotEmpty(PROJECT_NAME);
		assertNotEmpty(PROJECT_COLOR);
		return getValidationResult();
	}
	public ValidationResult validate(DoActivity activity) {
		 //VALID for TRAN
		initValidationOf(activity);
		assertNotEmpty(TASK_NAME);
		assertValidInteger(START_YEAR);
		assertValidInteger(START_MONTH);
		assertValidInteger(START_DAY);
		assertValidInteger(START_HOUR);
		assertValidInteger(START_MINUTE);
		assertValidInteger(START_SECOND);
		assertValidInteger(END_YEAR);
		assertValidInteger(END_MONTH);
		assertValidInteger(END_DAY);
		assertValidInteger(END_HOUR);
		assertValidInteger(END_MINUTE);
		assertValidInteger(END_SECOND);
		return getValidationResult();
	}
}
