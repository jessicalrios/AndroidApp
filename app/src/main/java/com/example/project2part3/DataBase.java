package com.example.project2part3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;

public class DataBase {
    public static final String DB_NAME = "BookRental.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "Users";

    public static final String USERNAME = "Username";
    public static final int USER_COL = 0;

    public static final String PASSWORD = "Password";
    public static final int PASSWORD_COL = 1;

    public static final String CREATE_USERSDB = "CREATE TABLE " + TABLE_USERS + " ( " + USERNAME + " TEXT, " + PASSWORD + " TEXT );";
    public static final String DROP_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    public static final String TABLE_BOOKS = "Books";

    public static final String BOOK_ID = "ID";
    public static final int B_ID_COL = 0;

    public static final String TITLE = "Book_Title";
    public static final int TITLE_COL = 1;

    public static final String AUTHOR = "Book_author";
    public static final int AUTHOR_COL = 2;

    public static final String FEE = "Fee";
    public static final int FEE_COL = 3;

    public static final String CREATE_BOOKSDB = "CREATE TABLE " + TABLE_BOOKS + " ( " + TITLE + " TEXT, " + AUTHOR + " TEXT, " + FEE + " REAL );";
    public static final String DROP_BOOKS = "DROP TABLE IF EXISTS " + TABLE_BOOKS;

    public static final String TABLE_ON_HOLD = "Book_On_Hold";

    public static final String RESERVER_ON_HOLD = "Reserver Name";
    public static final int ON_HOLD_COL = 0;

    public static final String BOOK_ON_HOLD = "Book title";
    public static final int BOOK_ON_HOLD_COL =1;

    public static final String PICK_UP_ON_HOLD = "PickUp";
    public static final int PICK_UP_ON_HOLD_COL = 2;

    public static final String CHECK_OUT_HOLD = "CheckOut";
    public static final int CHECK_OUT_HOLD_COL = 3;

    public static final String RESERV_NUM_HOLD = "Reservation Number";
    public static final int RESERV_NUM_HOLD_COL = 4;

    public static final String ACCUMULATE_ON_HOLD = "Accumulate";
    public static final int ACCUMULATE_ON_HOLD_COL = 5;

    public static final String CREATE_ON_HOLD = "CREATE TABLE " + TABLE_ON_HOLD + " ( " + RESERVER_ON_HOLD + " TEXT, " + BOOK_ON_HOLD + " TEXT, " + PICK_UP_ON_HOLD  + " TEXT, " + CHECK_OUT_HOLD + " TEXT, " + RESERV_NUM_HOLD  + " TEXT, " + ACCUMULATE_ON_HOLD + " REAL );";
    public static final String DROP_ON_HOLD = "DROP TABLE IF EXISTS " + TABLE_ON_HOLD;


    private static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context c, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(c, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_BOOKSDB);
            db.execSQL(CREATE_USERSDB);
            db.execSQL(CREATE_ON_HOLD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int v1, int v2){
            Log.d("Book Rental CSUMB ", "Upgrading database from version " + v1 + " to version " + v2);

            db.execSQL(DataBase.DROP_BOOKS);
            db.execSQL(DataBase.DROP_USERS);
            db.execSQL(CREATE_ON_HOLD);

            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DataBase(Context c){
        dbHelper = new DBHelper(c, DB_NAME, null, DB_VERSION);

        addUsersDB(new Users("Admin2", "Admin2"));
        addUsersDB(new Users("alice5", "csumb100"));
        addUsersDB(new Users("Brian7", "123abc"));
        addUsersDB(new Users("chris12", "CHRIS12"));
    }

    public boolean addUsersDB(Users users){
        String where = USERNAME + "= ?";
        String[] whereArgs = { users.getUserName() };

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, where, whereArgs, null, null, null);

        if(cursor.moveToFirst()){
            return false;
        }

        ContentValues cv = new ContentValues();

        cv.put(USERNAME, users.getUserName());
        cv.put(PASSWORD, users.getPassword());

        db = dbHelper.getWritableDatabase();

        long rowID = db.insert(TABLE_USERS, null, cv);

        if(db != null)
            db.close();

        return true;
    }

    public String getBooks(String pickupTimet, String returnTime) {
        String where = USERNAME + "= ?";

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);

        StringBuilder book = new StringBuilder();

        while (cursor.moveToNext()) {

            String b = getBookFromCursor(cursor);

            if( !b.isEmpty() )
                book.append(b);
        }

        if (db != null)
            db.close();

        return book.toString();

    }

    private static String getBookFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return "";
        }
        else {
            try {
                return ""; // come back to this
            }
            catch(Exception e) {
                return "";
            }
        }
    }


}
