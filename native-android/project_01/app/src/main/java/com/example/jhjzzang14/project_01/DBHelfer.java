package com.example.jhjzzang14.project_01;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelfer extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Chat.db";
    public static final String MOVIES_TABLE_NAME = "login";
    public static final String COLUMN_ID = "id";
    public DBHelfer(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table login " +
                        "(id integer primary key,id text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);
    }

    public boolean insertMovie(String id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        db.insert("login", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from login where id=" + id + "", null);

        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOVIES_TABLE_NAME);
        return numRows;
    }


    public Integer deleteMovie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies", "id = ? ", new String[]{Integer.toString(id)});
    }

    public ArrayList getAllMovies() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from login", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public int getPrimaryKey(int arg){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from login", null);
        res.moveToPosition(arg);

        return res.getInt(res.getColumnIndex(COLUMN_ID));
    }

    public boolean existName(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from login", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            if(name.equals(res.getString(res.getColumnIndex(COLUMN_ID))))
                return true;
            res.moveToNext();
        }
        return false;
    }

}
