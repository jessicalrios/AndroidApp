package com.example.project2part3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PlaceHold extends AppCompatActivity {

    private DataBase db;

    private EditText pickupTimeEditText, returnTimeEditText, bookTitleEditTexview;
    private TextView booksAvailableText;
    private Button searchTime, reserve, reserveButton;
    private EditText username, password;
    private Set<String> books;

    private Book book;
    private String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        pickupTimeEditText = (EditText) findViewById(R.id.pickupEditText);
        returnTimeEditText = (EditText) findViewById(R.id.returnEditText);
        bookTitleEditTexview = (EditText) findViewById(R.id.bookTitle);
        booksAvailableText = (TextView) findViewById(R.id.booksAvailable);
        searchTime = (Button) findViewById(R.id.searchTimeButton);
        reserve = (Button) findViewById(R.id.bookSearch);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        reserveButton = (Button) findViewById(R.id.reserveButton);

        reserve.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        reserveButton.setVisibility(View.INVISIBLE);

        books = new HashSet<String>();
        db = new DataBase(this);

        searchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p = pickupTimeEditText.getText().toString();
                String r = returnTimeEditText.getText().toString();

                if( !confirmCheckOut(p, r)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("You can only rent for 7 days");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                    return;
                }

                 books = db.getBooksAvailable(p, r);

                if(books.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("No book available during this time");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                    return;
                }

                String temp = "";

                Iterator<String> itr = books.iterator();

                while(itr.hasNext()){
                    temp = temp + itr.next() +'\n';
                }

                reserve.setVisibility(View.VISIBLE);
                booksAvailableText.setText(temp);
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bo = bookTitleEditTexview.getText().toString();

                if(!books.contains(bookTitleEditTexview.getText().toString())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("The book is not on the list");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                    return;
                }

                pickupTimeEditText.setVisibility(View.INVISIBLE);
                returnTimeEditText.setVisibility(View.INVISIBLE);
                bookTitleEditTexview.setVisibility(View.INVISIBLE);
                booksAvailableText.setVisibility(View.INVISIBLE);
                searchTime.setVisibility(View.INVISIBLE);
                reserve.setVisibility(View.INVISIBLE);

                reserveButton.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                username.setVisibility(View.VISIBLE);


            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u = username.getText().toString();
                String p = password.getText().toString();

                if( !db.getUser(u, p) ){
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Invalid information");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                    return;
                }
                else{

                    book = db.getBookInfo(bookTitleEditTexview.getText().toString());

                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder( PlaceHold.this );
                    builder1.setMessage(book.getTitle() + "\n" + book.getAuthor() + "\n" + pickupTimeEditText.getText().toString() + "\n" + returnTimeEditText.getText().toString() + "\n$" + book.getFee() * db.getHoursPublic( pickupTimeEditText.getText().toString(), returnTimeEditText.getText().toString()) );
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d("Hold", username.getText().toString());

                                    db.inserHold(username.getText().toString() , book, pickupTimeEditText.getText().toString(), returnTimeEditText.getText().toString());

                                    startActivity(new Intent(PlaceHold.this, MainActivity.class));
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(PlaceHold.this, MainActivity.class));
                                }
                            });

                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();

                    return;
                }
            }
        });

    }

    private static Boolean confirmCheckOut(String checkout, String checkin)
    {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date dateCheckin = (Date)formatter.parse(checkout);
            Date userCheckout = (Date)formatter.parse(checkin);

            Calendar cal =Calendar.getInstance();
            cal.setTime(userCheckout);
            cal.setTime(dateCheckin);
            cal.add(Calendar.DATE, 7);

            Date maxCheckout = cal.getTime();

            if (userCheckout.compareTo(maxCheckout) > 0){ return false; }
            else{ return true; }

        } catch (ParseException e)
        {System.out.println("Exception: " + e);  }
        return false;
    }

}
