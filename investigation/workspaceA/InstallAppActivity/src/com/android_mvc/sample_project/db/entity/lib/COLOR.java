package com.android_mvc.sample_project.db.entity.lib;

import android.content.ContentValues;
import android.database.Cursor;

public class COLOR extends LogicalEntity<COLOR> {

	// Intent経由でエンティティを運搬可能にするために
	private static final long serialVersionUID = 1L;

	// テーブル
	@Override
	public String tableName() {
		return "COLOR";
	}

	// カラム
	private String COLOR_NAME = "COLOR_NAME";
	private String COLOR_FLAVOR= "COLOR_FLAVOR";
	private String COLOR_CODE = "COLOR_CODE";

	@Override
	public String[] columns() {
		return new String[] { "id" ,COLOR_NAME, COLOR_FLAVOR, COLOR_CODE,};
	}

	// メンバ
	private String color_name = null;
	private String color_flavor = null;
	private int color_code = 0;
	
	@Override
	public COLOR logicalFromPhysical(Cursor c) {
		setId(c.getLong(0));
		setColor_name(c.getString(1));
		setColor_flavor(c.getString(2));
		setColor_code(Integer.valueOf((String)c.getString(3)));
		
		return this;
	}
	// Getter Setter
	
	public int getColor_code() {
		return color_code;
	}

	public String getCOLOR_NAME() {
		return COLOR_NAME;
	}

	public String getCOLOR_FLAVOR() {
		return COLOR_FLAVOR;
	}

	public String getCOLOR_CODE() {
		return COLOR_CODE;
	}

	public void setColor_code(int color_code) {
		this.color_code = color_code;
	}

	public String getColor_name() {
		return color_name;
	}

	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}

	public String getColor_flavor() {
		return color_flavor;
	}

	public void setColor_flavor(String color_flavor) {
		this.color_flavor = color_flavor;
	}

	@Override
	protected ContentValues toPhysicalEntity(ContentValues values) {
		if (getId() != null) {
			values.put("id", getId());
		}
		values.put(COLOR_NAME, getColor_name());
		values.put(COLOR_FLAVOR, getColor_name());
		values.put(COLOR_CODE, getColor_code());

		return values;
	}

}
