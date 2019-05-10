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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    public static final String TITLE = "Book_Title";
    public static final int TITLE_COL = 0;

    public static final String AUTHOR = "Book_author";
    public static final int AUTHOR_COL = 1;

    public static final String FEE = "Fee";
    public static final int FEE_COL = 2;

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
            db.execSQL(DataBase.DROP_ON_HOLD);

            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DataBase(Context c){
        dbHelper = new DBHelper(c, DB_NAME, null, DB_VERSION);

        addUsersDB(new Users("alice5", "csumb100"));
        addUsersDB(new Users("Brian7", "123abc"));
        addUsersDB(new Users("chris12", "CHRIS12"));

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, null, null, null, null, null, null);

        if( !cursor.moveToFirst() ){
            addBookDB("Hot Java", "S. Narayanan", .05);
            addBookDB("Fun Java", "Y. Byun", 1);
            addBookDB("Algorithm for Java", "K. Alice", .25);
        }
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

    public boolean addBookDB(String title, String author, double fee){

        db = dbHelper.getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TITLE, title);
        cv.put(AUTHOR, author);
        cv.put(FEE, fee);

        db = dbHelper.getWritableDatabase();

        long rowID = db.insert(TABLE_BOOKS, null, cv);

        if(db != null)
            db.close();

        return true;
    }

    public Set<String> getBooksAvailable(String p, String r) {

        ArrayList<String> taken = new ArrayList<>();
        ArrayList<String> books = new ArrayList<>();
        Set<String> goodBooks = new HashSet<String>();

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {  //geting all book
            books.add(getBookFromCursor(cursor));
        }

        cursor = db.query(TABLE_ON_HOLD, null, null, null, null, null, null);

        while (cursor.moveToNext()) {  //geting all book
            String temp = getBooksTaken(cursor, p, r);

            if(!temp.isEmpty()) taken.add(temp);
        }

        for(int i=0; i<books.size(); i++){
            if( !taken.contains( books.get(i) ) )
                goodBooks.add( books.get(i) );
        }

        if (db != null)
            db.close();

        return goodBooks;

    }


    private static String getBooksTaken(Cursor cursor, String p, String r){
        if (cursor == null || cursor.getCount() == 0){
            return "";
        }
        else {
            try {

                    String Hold = cursor.getString(BOOK_ON_HOLD_COL);
                    String pickupHold = cursor.getString(PICK_UP_ON_HOLD_COL);
                    String returnHold = cursor.getString(CHECK_OUT_HOLD_COL);

                    if( checkInterference(p, r, pickupHold, returnHold) ){
                        return "";
                    }

                return Hold;
            }
            catch(Exception e) {
                return "";
            }
        }
    }

    private static String getBookFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return "";
        }
        else {
            try {
                return cursor.getString(TITLE_COL);
            }
            catch(Exception e) {
                return "";
            }
        }
    }

    public boolean getUser(String u, String p){

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            boolean temp = findUserFromCursor(cursor, u, p);

            if(temp) {
                return true;
            }
        }

        if (db != null)
            db.close();

        return false;
    }

    public static boolean findUserFromCursor(Cursor cursor, String  u, String p){
        if (cursor == null || cursor.getCount() == 0){
            return false;
        }
        else {
            try {

                if( cursor.getString(USER_COL).equals(u) && cursor.getString(PASSWORD_COL).equals(p))
                    return true;

                return false;
            }
            catch(Exception e) {
                return false;
            }
        }
    }

    public Book getBookInfo( String b){
        String where = TITLE + "= ?";
        String[] whereArgs = { b };

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, null,
                where, whereArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            return new Book(cursor.getString(TITLE_COL) , cursor.getString(AUTHOR_COL), cursor.getDouble(FEE_COL));
        }
        if (db != null)
            db.close();

        return null;
    }

    public static Book findBookInfoFromCursor(Cursor cursor, String b){
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Log.d(b, cursor.getString(TITLE_COL));
                if( cursor.getString(TITLE_COL).equals(b) ) {
                    Log.d(b, "djfklsdjfkljdskjflsdkfjdsljfldsjlfjsdljfkldsjfjdslfjklsdjflkdsjflkdsjlfk");
                    new Book(cursor.getString(TITLE_COL), cursor.getString(AUTHOR_COL), cursor.getDouble(FEE_COL));
                }

                return null;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public void inserHold(Book book, String p, String r){

        db = dbHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        Random random = new Random();
        int reserNum = random.nextInt(999999) + 100000;

        cv.put(RESERVER_ON_HOLD, "HOLD");
        cv.put(BOOK_ON_HOLD, book.getTitle());
        cv.put(PICK_UP_ON_HOLD, p);
        cv.put(CHECK_OUT_HOLD, r);
        cv.put(RESERVER_ON_HOLD, Integer.toString(reserNum));
        cv.put(ACCUMULATE_ON_HOLD, book.getFee());

        db = dbHelper.getWritableDatabase();

        long rowID = db.insert(TABLE_ON_HOLD, null, cv);

        if(db != null)
            db.close();

    }
}
