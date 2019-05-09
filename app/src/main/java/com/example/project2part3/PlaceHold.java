package com.example.project2part3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import	android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

public class PlaceHold extends AppCompatActivity {

    private DataBase db;

    private EditText pickupTimeEditText, returnTimeEditText, bookTitleEditTexview;
    private TextView booksAvailableText;
    private Button searchTime, reserve;

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

        reserve.setVisibility(View.INVISIBLE);

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
                }

//                 books = db.getBooks(p, r);
//
//                if(books.isEmpty()){
//
//                }
//
//                reserve.setVisibility(View.VISIBLE);
//                booksAvailableText.setText(books);
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
