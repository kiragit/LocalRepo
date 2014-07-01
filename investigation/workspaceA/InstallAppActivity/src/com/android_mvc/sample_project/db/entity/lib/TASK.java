package com.android_mvc.sample_project.db.entity.lib;

import android.content.ContentValues;
import android.database.Cursor;

public class TASK extends LogicalEntity<TASK> {

	// Intent経由でエンティティを運搬可能にするために
	private static final long serialVersionUID = 1L;

	// テーブル
	@Override
	public String tableName() {
		return "TASK";
	}

	// カラム
	private String TASK_CODE = "TASK_CODE";
	private String PROJECT_CODE= "PROJECT_CODE";
	private String TASK_NAME = "TASK_NAME";
	private String TASK_TYPE = "TASK_TYPE";
	private String TASK_COLOR = "TASK_COLOR";
	private String TASK_STATE = "TASK_STATE";
	
	
	// Getter Setter
	
	
	public String getTask_name() {
		return task_name;
	}

	public String getTASK_CODE() {
		return TASK_CODE;
	}

	public String getPROJECT_CODE() {
		return PROJECT_CODE;
	}

	public String getTASK_NAME() {
		return TASK_NAME;
	}

	public String getTASK_TYPE() {
		return TASK_TYPE;
	}

	public String getTASK_COLOR() {
		return TASK_COLOR;
	}

	public String getTASK_STATE() {
		return TASK_STATE;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public int getTask_color() {
		return task_color;
	}

	public void setTask_color(int task_color) {
		this.task_color = task_color;
	}

	public int getTask_type() {
		return task_type;
	}

	public void setTask_type(int task_type) {
		this.task_type = task_type;
	}

	public int getTask_code() {
		return task_code;
	}

	public void setTask_code(int task_code) {
		this.task_code = task_code;
	}
	public int getProject_code() {
		return project_code;
	}

	public void setProject_code(int project_code) {
		this.project_code = project_code;
	}

	public int getTask_state() {
		return task_state;
	}

	public void setTask_state(int task_state) {
		this.task_state = task_state;
	}

	@Override
	public String[] columns() {
		return new String[] { 
				"id",
				getTASK_CODE(),
				getPROJECT_CODE(),
				getTASK_NAME(),
				getTASK_TYPE(),
				getTASK_COLOR(),
				getTASK_STATE()
				};
	}

	// メンバ
	private int task_code = 0;
	private int project_code =0;
	private String task_name = null;
	private int task_color = 0;
	private int    task_type = 0;
	private int task_state = 0;

	@Override
	public TASK logicalFromPhysical(Cursor c) {
		setId(c.getLong(0));
		setTask_code(Integer.valueOf((String)c.getString(1)));
		setProject_code(Integer.valueOf((String)c.getString(2)));
		setTask_name(c.getString(3));
		setTask_type(c.getInt(4));
		setTask_color(Integer.valueOf((String)c.getString(5)));
		setTask_state(Integer.valueOf((String)c.getString(6)));

		return this;
	}

	@Override
	protected ContentValues toPhysicalEntity(ContentValues values) {
		if (getId() != null) {
			values.put("id", getId());
		}
		values.put(TASK_CODE, getTask_code());
		values.put(PROJECT_CODE, getProject_code());
		values.put(TASK_NAME, getTask_name());
		values.put(TASK_TYPE, getTask_type());
		values.put(TASK_COLOR, getTask_color());
		values.put(TASK_STATE, getTask_state());

		return values;
	}

}
