package com.android_mvc.sample_project.db.schema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android_mvc.framework.db.schema.AbstractSchemaDefinition;
import com.android_mvc.framework.db.schema.RDBColumn;
import com.android_mvc.framework.db.schema.RDBTable;
import com.android_mvc.sample_project.db.entity.lib.COLOR;
import com.android_mvc.sample_project.db.entity.lib.PROJECT;
import com.android_mvc.sample_project.db.entity.lib.TASK;
import com.android_mvc.sample_project.db.entity.lib.TRAN;
import com.android_mvc.sample_project.db.entity.lib.TYPE;

/**
 * AP側のテーブル構造と初期データを定義。
 * @author id:language_and_engineering
 *
 */
public class SchemaDefinition extends AbstractSchemaDefinition
{
    // NOTE:
    // ・各テーブルの主キーは「id」。
    // ・SQLiteのカラムで定義可能な型については，下記を参照
    //     http://www.sqlite.org/datatype3.html
    // ・アプリのインストール（初期化）アクティビティから呼び出される。

	private TASK t = new TASK();
	private PROJECT p = new PROJECT();
	private TRAN tran = new TRAN();
	private TYPE type = new TYPE();
	private COLOR color = new COLOR();
	
	//TASK
	private String TASK_NAME = t.getTASK_NAME();
	private String TASK_COLOR = t.getTASK_COLOR();
	private String TASK_TYPE = t.getTASK_TYPE();
	private String TASK_CODE = t.getTASK_CODE();
	private String TASK_STATE = t.getTASK_STATE();
	//PROJECT
	private String PROJECT_CODE = p.getPROJECT_CODE();
	private String PROJECT_NAME = p.getPROJECT_NAME();
	private String PROJECT_COLOR = p.getPROJECT_COLOR();
	private String PROJECT_STATE = p.getPROJECT_STATE();
	//TRAN
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
	//TYPE
	private String TYPE_NAME = type.getTYPE_NAME();
	private String TYPE_CODE = type.getTYPE_CODE();
	private String TYPE_FLAVOR = type.getTYPE_FLAVOR();
	//COLOR
	private String COLOR_CODE = color.getCOLOR_CODE();
	private String COLOR_NAME = color.getCOLOR_NAME();
	private String COLOR_FLAVOR = color.getCOLOR_FLAVOR();
			
    @Override
    public void define_tables()
    {
        // PROJECT
        new RDBTable(this)
            .nameIs( new PROJECT().tableName() )
            .columns(new RDBColumn[]{
                new RDBColumn().nameIs("id").primaryKey(),
                new RDBColumn().nameIs(PROJECT_CODE).comment("コード").typeIs("integer").notNull(),
                new RDBColumn().nameIs(PROJECT_NAME).comment("名前").typeIs("text").notNull(),
                new RDBColumn().nameIs(PROJECT_COLOR).comment("カラー").typeIs("ingeger"),
                new RDBColumn().nameIs(PROJECT_STATE).comment("状態").typeIs("integer")
            })
            .create()
        ;
        // TASK
        new RDBTable(this)
            .nameIs( new TASK().tableName() )
            .columns(new RDBColumn[]{
                new RDBColumn().nameIs("id").primaryKey(),
                new RDBColumn().nameIs(TASK_CODE).comment("タスクコード").typeIs("integer").notNull(),
                new RDBColumn().nameIs(PROJECT_CODE).comment("プロジェクトコード").typeIs("integer"),
                new RDBColumn().nameIs(TASK_NAME).comment("名前").typeIs("text").notNull(),
                new RDBColumn().nameIs(TASK_TYPE).comment("タイプ").typeIs("integer"),
                new RDBColumn().nameIs(TASK_COLOR).comment("カラー").typeIs("integer"),
                new RDBColumn().nameIs(TASK_STATE).comment("論理削除フラグ").typeIs("integer")
            })
            .create()
        ;
        // 日々トランザクション
        new RDBTable(this)
            .nameIs( new TRAN().tableName() )
            .columns(new RDBColumn[]{
                new RDBColumn().nameIs("id").primaryKey(),
                new RDBColumn().nameIs(TASK_CODE).comment("タスクコード").typeIs("integer").notNull(),
                new RDBColumn().nameIs(START_YEAR).comment("開始年").typeIs("integer"),
                new RDBColumn().nameIs(START_MONTH).comment("開始月").typeIs("integer"),
                new RDBColumn().nameIs(START_DAY).comment("開始日").typeIs("integer"),
                new RDBColumn().nameIs(START_HOUR).comment("開始時").typeIs("integer"),
                new RDBColumn().nameIs(START_MINUTE).comment("開始分").typeIs("integer"),
                new RDBColumn().nameIs(START_SECOND).comment("開始秒").typeIs("integer"),
                new RDBColumn().nameIs(END_YEAR).comment("終了年").typeIs("integer"),
                new RDBColumn().nameIs(END_MONTH).comment("終了月").typeIs("integer"),
                new RDBColumn().nameIs(END_DAY).comment("終了日").typeIs("integer"),
                new RDBColumn().nameIs(END_HOUR).comment("終了時").typeIs("integer"),
                new RDBColumn().nameIs(END_MINUTE).comment("終了分").typeIs("integer"),
                new RDBColumn().nameIs(END_SECOND).comment("終了秒").typeIs("integer"),
            })
            .create()
        ;
        // TYPE
        new RDBTable(this)
            .nameIs( new TYPE().tableName() )
            .columns(new RDBColumn[]{
                new RDBColumn().nameIs("id").primaryKey(),
                new RDBColumn().nameIs(TYPE_CODE).comment("タイプコード").typeIs("integer").notNull(),
                new RDBColumn().nameIs(TYPE_NAME).comment("タイプ名").typeIs("text").notNull(),
                new RDBColumn().nameIs(TYPE_FLAVOR).comment("属性").typeIs("text").notNull(),
            })
            .create()
        ;
        // COLOR
        new RDBTable(this)
            .nameIs( new COLOR().tableName() )
            .columns(new RDBColumn[]{
                new RDBColumn().nameIs("id").primaryKey(),
                new RDBColumn().nameIs(COLOR_NAME).comment("色名").typeIs("text").notNull(),
                new RDBColumn().nameIs(COLOR_FLAVOR).comment("属性").typeIs("text"),
                new RDBColumn().nameIs(COLOR_CODE).comment("カラーコード").typeIs("integer").notNull(),
            })
            .create()
        ;
    }


    @Override
    public void init_db_data(SQLiteDatabase db, Context context)
    {
        //db.execSQL("insert into TASK (TASK_CODE, TASK_NAME, TASK_TYPE, TASK_COLOR) values ('0000', 'メール確認', 1,'青');");
        db.execSQL(
        		"insert into TASK (" +
        		"PROJECT_CODE," +
        		"TASK_CODE, " +
        		"TASK_NAME, " +
        		"TASK_TYPE, " +
        		"TASK_COLOR," +
        		"TASK_STATE) " +
        		
        		"values (" +
        		"1," +
        		"1," +
        		" 'メール確認', " +
        		"1," +
        		"12345678," +
        		"1);"
        		);
        db.execSQL(
        		"insert into TASK (" +
        		"PROJECT_CODE," +
        		"TASK_CODE, " +
        		"TASK_NAME, " +
        		"TASK_TYPE, " +
        		"TASK_COLOR," +
        		"TASK_STATE) " +
        		
        		"values (" +
        		"2," +
        		"2," +
        		" 'スケジュール', " +
        		"1," +
        		"12345678," +
        		"1);"
        		);
        db.execSQL(
        		"insert into PROJECT (" +
        		"PROJECT_CODE," +
        		"PROJECT_NAME, " +
        		"PROJECT_COLOR, " +
        		"PROJECT_STATE )" +
        		
        		"values (" +
        		"0," +
        		"'サンプル'," +
        		"12345678, " +
        		"1);"
        		);
    }

}
