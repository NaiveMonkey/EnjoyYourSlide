package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 김새미루 on 2016-06-27.
 */
public class MyDBManager extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SHORTCUT = "create table SHORTCUT(_id integer primary key, packageName text, appIcon blob, appName text, row integer, col integer);";
    private static final String CREATE_TABLE_TRANSPARENT = "create table TRANSPARENT(_id integer primary key, row integer, col integer);";

    public MyDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHORTCUT);
        db.execSQL(CREATE_TABLE_TRANSPARENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists SHORTCUT");
        db.execSQL("drop table if exists TRANSPARENT");
        onCreate(db);
    }


}
