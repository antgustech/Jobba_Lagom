package com.example.i7.jobbalagom.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Strandberg95 on 2016-05-01.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "USERINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + UserContract.NewUserInfo.TABLE_NAME
            + "( " + UserContract.NewUserInfo.USER_NAME + " varchar(200),"
            + UserContract.NewUserInfo.USER_EARNED + " FLOAT, "
            + UserContract.NewUserInfo.USER_INCOME + " FLOAT, "
            + UserContract.NewUserInfo.USER_TAX + " FLOAT);";

    public UserDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DBTAG", "Database created / Opened...");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DBTAG", "Table Created...");
    }

    public void addInformations(String name, float earned, float income, float tax, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.NewUserInfo.USER_NAME,name);
        contentValues.put(UserContract.NewUserInfo.USER_EARNED,earned);
        contentValues.put(UserContract.NewUserInfo.USER_INCOME,income);
        contentValues.put(UserContract.NewUserInfo.USER_TAX, tax);
        db.insert(UserContract.NewUserInfo.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
