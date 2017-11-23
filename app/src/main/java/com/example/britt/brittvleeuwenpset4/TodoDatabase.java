package com.example.britt.brittvleeuwenpset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by britt on 20-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {

    private static String TABLE_NAME = "todos";
    private static TodoDatabase instance;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "Create table " + TABLE_NAME + " (_id integer primary key, title text, completed boolean)";
        sqLiteDatabase.execSQL(create);
    }


    private TodoDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
//        sqLiteDatabase.execSQL("update " + TABLE_NAME + "set _id = il where _id = i");
    }




    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context);
        }
        return instance;
    }
    public boolean insert(String title, boolean completed) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("completed", completed);

        Log.d("TodoDatabase", "insert: adding " + title + "to " + TABLE_NAME);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor selectAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        return c;
    }

    public void Delete(long id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String create = "Delete from " + TABLE_NAME + " where _id= '"+ id + "';";
        sqLiteDatabase.execSQL(create);
    }

    public void Update(long id, boolean status){
        Integer completed;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (status) {
            completed = 0;
        }
        else {
            completed = 1;
        }
        String create = "Update " + TABLE_NAME + " Set completed = '" + completed + "' where _id='"+ id + "';";
        sqLiteDatabase.execSQL(create);
    }
}
