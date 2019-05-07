package com.example.project2part3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class DataBase {
    public static final String DB_NAME = "BookRental.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "Users";

    public static final String ID = "ID";
    public static final int ID_COL = 0;

    public static final String USERNAME = "Username";
    public static final int USER_COL = 1;

    public static final String PASSWORD = "Password";
    public static final int PASSWORD_COL = 2;

//    public static final String TABLE_BOOKS = "Books";

//    public static final String BOOK_ID = "ID";
//    public static final int B_ID_COL = 0;

//    public static final String TITLE = "Book_Title";
//    public static final int TITLE_COL = 1;

//    public static final String AUTHOR = "Book_author";
//    public static final int AUTHOR_COL = 2;

//    public static final String FEE = "Fee";
//    public static final int FEE_COL = 3;



    //Create and Drop table if Exist
//    public static final String CREATE_BOOKSDB = "CREATE TABLE " + TABLE_BOOKS + " ( " + TITLE + " TEXT, " + AUTHOR + " TEXT, " + FEE + " REAL );";
    public static final String CREATE_USERSDB = "CREATE TABLE " + TABLE_USERS + " ( " + ID + " INTEGER, " + USERNAME + " TEXT, " + PASSWORD + " TEXT );";

//    public static final String DROP_BOOKS = "DROP TABLE IF EXISTS " + TABLE_BOOKS;
    public static final String DROP_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    private static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context c, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(c, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
//            db.execSQL(CREATE_BOOKSDB);
            db.execSQL(CREATE_USERSDB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int v1, int v2){
            Log.d("Book Rental CSUMB ", "Upgrading database from version " + v1 + " to version " + v2);

//            db.execSQL(DataBase.DROP_BOOKS);
            db.execSQL(DataBase.DROP_USERS);

            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DataBase(Context c){
        dbHelper = new DBHelper(c, DB_NAME, null, DB_VERSION);
    }

    public long addUsersDB(Users users){
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, users.getUserName());
        cv.put(PASSWORD, users.getPassword());
//        cv.put(DATE, users.getDate());

        db = dbHelper.getWritableDatabase();

        long rowID = db.insert(TABLE_USERS, null, cv);

        if(db != null)
            db.close();

        return rowID;
    }
}
