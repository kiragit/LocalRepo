package com.android_mvc.sample_project.db.entity.lib;

import android.content.ContentValues;
import android.database.Cursor;

public class TRAN extends LogicalEntity<TRAN> {

	// Intent経由でエンティティを運搬可能にするために
	private static final long serialVersionUID = 1L;

	// テーブル
	@Override
	public String tableName() {
		return "TRAN";
	}

	// カラム
	private String TASK_CODE = "TASK_CODE";
	private String START_YEAR = "START_YEAR";
	private String START_MONTH = "START_MONTH";
	private String START_DAY = "START_DAY";
	private String START_HOUR = "START_HOUR";
	private String START_MINUTE = "START_MINUTE";
	private String START_SECOND = "START_SECOND";
	private String END_YEAR = "END_YEAR";
	private String END_MONTH = "END_MONTH";
	private String END_DAY = "END_DAY";
	private String END_HOUR = "END_HOUR";
	private String END_MINUTE = "END_MINUTE";
	private String END_SECOND = "END_SECOND";
	
	@Override
	public String[] columns() {
		return new String[] { 
				"id",
				TASK_CODE,
				START_YEAR,
				START_MONTH,
				START_DAY,
				START_HOUR,
				START_MINUTE,
				START_SECOND,
				END_YEAR,
				END_MONTH,
				END_DAY,
				END_HOUR,
				END_MINUTE,
				END_SECOND	
				};
	}

	// メンバ
	private int task_code = 0;
	private int start_year = 0;
	private int start_month = 0;
	private int start_day = 0;
	private int start_hour = 0;
	private int start_minute = 0;
	private int start_second = 0;
	private int end_year = 0;
	private int end_month = 0;
	private int end_day = 0;
	private int end_hour = 0;
	private int end_minute = 0;
	private int end_second = 0;

	@Override
	public TRAN logicalFromPhysical(Cursor c) {
		setId(c.getLong(0));
		setTask_code(Integer.valueOf((String)c.getString(1)));
		setStart_year(Integer.valueOf((String)c.getString(2)));
		setStart_month(Integer.valueOf((String)c.getString(3)));
		setStart_day(Integer.valueOf((String)c.getString(4)));
		setStart_hour(Integer.valueOf((String)c.getString(5)));
		setStart_minute(Integer.valueOf((String)c.getString(6)));
		setStart_second(Integer.valueOf((String)c.getString(7)));
		setEnd_year(Integer.valueOf((String)c.getString(8)));
		setEnd_month(Integer.valueOf((String)c.getString(9)));
		setEnd_day(Integer.valueOf((String)c.getString(10)));
		setEnd_hour(Integer.valueOf((String)c.getString(11)));
		setEnd_minute(Integer.valueOf((String)c.getString(12)));
		setEnd_second(Integer.valueOf((String)c.getString(13)));

		return this;
	}
	
	// Getter Setter
	
	public String getTASK_CODE() {
		return TASK_CODE;
	}

	public String getSTART_YEAR() {
		return START_YEAR;
	}

	public String getSTART_MONTH() {
		return START_MONTH;
	}

	public String getSTART_DAY() {
		return START_DAY;
	}

	public String getSTART_HOUR() {
		return START_HOUR;
	}

	public String getSTART_MINUTE() {
		return START_MINUTE;
	}

	public String getSTART_SECOND() {
		return START_SECOND;
	}

	public String getEND_YEAR() {
		return END_YEAR;
	}

	public String getEND_MONTH() {
		return END_MONTH;
	}

	public String getEND_DAY() {
		return END_DAY;
	}

	public String getEND_HOUR() {
		return END_HOUR;
	}

	public String getEND_MINUTE() {
		return END_MINUTE;
	}

	public String getEND_SECOND() {
		return END_SECOND;
	}

	public int getTask_code() {
		return task_code;
	}

	public void setTask_code(int task_code) {
		this.task_code = task_code;
	}

	public int getStart_year() {
		return start_year;
	}

	public void setStart_year(int start_year) {
		this.start_year = start_year;
	}

	public int getStart_month() {
		return start_month;
	}

	public void setStart_month(int start_month) {
		this.start_month = start_month;
	}

	public int getStart_day() {
		return start_day;
	}

	public void setStart_day(int start_day) {
		this.start_day = start_day;
	}

	public int getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(int start_hour) {
		this.start_hour = start_hour;
	}

	public int getStart_minute() {
		return start_minute;
	}

	public void setStart_minute(int start_minute) {
		this.start_minute = start_minute;
	}

	public int getStart_second() {
		return start_second;
	}

	public void setStart_second(int start_second) {
		this.start_second = start_second;
	}

	public int getEnd_year() {
		return end_year;
	}

	public void setEnd_year(int end_year) {
		this.end_year = end_year;
	}

	public int getEnd_month() {
		return end_month;
	}

	public void setEnd_month(int end_month) {
		this.end_month = end_month;
	}

	public int getEnd_day() {
		return end_day;
	}

	public void setEnd_day(int end_day) {
		this.end_day = end_day;
	}

	public int getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(int end_hour) {
		this.end_hour = end_hour;
	}

	public int getEnd_minute() {
		return end_minute;
	}

	public void setEnd_minute(int end_minute) {
		this.end_minute = end_minute;
	}

	public int getEnd_second() {
		return end_second;
	}

	public void setEnd_second(int end_second) {
		this.end_second = end_second;
	}

	@Override
	protected ContentValues toPhysicalEntity(ContentValues values) {
		if (getId() != null) {
			values.put("id", getId());
		}
		values.put(TASK_CODE, getTask_code());
		values.put(START_YEAR, getStart_year());
		values.put(START_MONTH, getStart_month());
		values.put(START_DAY, getStart_day());
		values.put(START_HOUR, getStart_hour());
		values.put(START_MINUTE, getStart_minute());
		values.put(START_SECOND, getStart_second());
		values.put(END_YEAR, getEnd_year());
		values.put(END_MONTH, getEnd_month());
		values.put(END_DAY, getEnd_day());
		values.put(END_HOUR, getEnd_hour());
		values.put(END_MINUTE, getEnd_minute());
		values.put(END_SECOND, getEnd_second());

		return values;
	}

}
