package com.example.car_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "InnerDB";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;


    public static final class Databases {

        public static final class CreateDB implements BaseColumns {
            public static final String USERID = "userid";
            public static final String PWD = "pwd";
            public static final String PHONENUM = "phonenum";
            public static final String AUTHORITY = "authority";

            public static final String _TABLENAME0 = "usertable";
            public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                    + _ID + " integer primary key autoincrement, "
                    + USERID + " text not null , "
                    + PWD + " text not null , "
                    + PHONENUM + " text not null , "
                    + AUTHORITY + " text not null );";
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Databases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table if exists " + Databases.CreateDB._TABLENAME0);
            onCreate(db);
        }

    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }


    //사용자 등록 DB에 넣기
    //INSERT
    public long insert(String id, String pwd, String phone, String auth) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Databases.CreateDB.USERID, id);
        contentValues.put(Databases.CreateDB.PWD, pwd);
        contentValues.put(Databases.CreateDB.PHONENUM, phone);
        contentValues.put(Databases.CreateDB.AUTHORITY, auth);

        return mDB.insert(Databases.CreateDB._TABLENAME0, null, contentValues);
    }

    public void getAllData() {
        Cursor res = mDB.rawQuery("SELECT * FROM " + Databases.CreateDB._TABLENAME0, null);
        while (res.moveToNext()) {
            String s = res.getString(0)+ " ID : " + res.getString(1) + " PWD : " + res.getString(2) + " PhoneNum : " + res.getString(3) + " Authority : " + res.getString(4);
            System.out.println(s);
        }
    }

    public int Isthere(String x, String y){
        String s = null;
        String query = "SELECT * FROM " + Databases.CreateDB._TABLENAME0 + " WHERE USERID = "+ "\"" + x +"\""+"AND PWD = '" + y + "'";
        System.out.println("========"+query);
        Cursor res = mDB.rawQuery(query, null);
        while (res.moveToNext()) {
            s = res.getString(0)+ " ID : " + res.getString(1) + " PWD : " + res.getString(2) + " PhoneNum : " + res.getString(3) + " Authority : " + res.getString(4);
            System.out.println(s);
        }
        if(s!=null) return 1;
        else return 0;
    }

}
