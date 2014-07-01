package com.android_mvc.sample_project.db.entity.lib;

import android.content.ContentValues;
import android.database.Cursor;

public class TYPE extends LogicalEntity<TYPE> {

	// Intent経由でエンティティを運搬可能にするために
	private static final long serialVersionUID = 1L;

	// テーブル
	@Override
	public String tableName() {
		return "TYPE";
	}

	// カラム
	private String TYPE_CODE = "TYPE_CODE";
	private String TYPE_NAME = "TYPE_NAME";
	private String TYPE_FLAVOR= "TYPE_FLAVOR";

	@Override
	public String[] columns() {
		return new String[] {
				"id",
				getTYPE_CODE(),
				getTYPE_NAME(),
				getTYPE_FLAVOR()};
	}

	// メンバ
	private int type_code = 0;
	private String type_name = null;
	private String type_flavor = null;

	@Override
	public TYPE logicalFromPhysical(Cursor c) {
		setId(c.getLong(0));
		setType_code(Integer.valueOf((String)c.getString(1)));
		setType_name(c.getString(2));
		setType_flavor(c.getString(3));

		return this;
	}

	// Getter Setter
	
	public String getType_flavor() {
		return type_flavor;
	}

	public String getTYPE_CODE() {
		return TYPE_CODE;
	}

	public String getTYPE_NAME() {
		return TYPE_NAME;
	}

	public String getTYPE_FLAVOR() {
		return TYPE_FLAVOR;
	}

	public void setType_flavor(String type_flavor) {
		this.type_flavor = type_flavor;
	}

	public int getType_code() {
		return type_code;
	}

	public void setType_code(int type_code) {
		this.type_code = type_code;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	@Override
	protected ContentValues toPhysicalEntity(ContentValues values) {
		if (getId() != null) {
			values.put("id", getId());
		}
		values.put(TYPE_CODE, getType_code());
		values.put(TYPE_NAME, getType_name());
		values.put(TYPE_FLAVOR, getType_name());

		return values;
	}

}
