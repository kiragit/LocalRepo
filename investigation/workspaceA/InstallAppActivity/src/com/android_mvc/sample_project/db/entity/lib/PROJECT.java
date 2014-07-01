package com.android_mvc.sample_project.db.entity.lib;

import android.content.ContentValues;
import android.database.Cursor;

public class PROJECT extends LogicalEntity<PROJECT> {

	// Intent経由でエンティティを運搬可能にするために
	private static final long serialVersionUID = 1L;

	// テーブル
	@Override
	public String tableName() {
		return "PROJECT";
	}

	// カラム
	private String PROJECT_CODE = "PROJECT_CODE";
	private String PROJECT_NAME = "PROJECT_NAME";
	private String PROJECT_COLOR = "PROJECT_COLOR";
	private String PROJECT_STATE= "PROJECT_STATE";

	@Override
	public String[] columns() {
		return new String[] {
				"id",
				getPROJECT_CODE(),
				getPROJECT_NAME(),
				getPROJECT_COLOR(),
				getPROJECT_STATE()
				};
	}

	// メンバ
	private int project_code = 0;
	private String project_name = null;
	private int project_color = 99999999;
	private int project_state= 0;

	// Getter Setter
	
	public String getProject_name() {
		return project_name;
	}

	public String getPROJECT_CODE() {
		return PROJECT_CODE;
	}

	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}

	public String getPROJECT_COLOR() {
		return PROJECT_COLOR;
	}

	public String getPROJECT_STATE() {
		return PROJECT_STATE;
	}

	public int getProject_state() {
		return project_state;
	}

	public void setProject_state(int pROJECT_state) {
		project_state = pROJECT_state;
	}

	public void setProject_name(String PROJECT_name) {
		this.project_name = PROJECT_name;
	}

	public int getProject_color() {
		return project_color;
	}

	public void setProject_color(int PROJECT_color) {
		this.project_color = PROJECT_color;
	}

	public int getProject_code() {
		return project_code;
	}

	public void setProject_code(int PROJECT_code) {
		this.project_code = PROJECT_code;
	}

	@Override
	public PROJECT logicalFromPhysical(Cursor c) {
		setId(c.getLong(0));
		setProject_code(Integer.valueOf((String)c.getString(1)));
		setProject_name(c.getString(2));
		setProject_color(Integer.valueOf((String)c.getString(3)));
		setProject_state(Integer.valueOf((String)c.getString(4)));

		return this;
	}

	@Override
	protected ContentValues toPhysicalEntity(ContentValues values) {
		if (getId() != null) {
			values.put("id", getId());
		}
		values.put(PROJECT_CODE, getProject_code());
		values.put(PROJECT_NAME, getProject_name());
		values.put(PROJECT_COLOR, getProject_color());
		values.put(PROJECT_STATE, getProject_state());

		return values;
	}

}
