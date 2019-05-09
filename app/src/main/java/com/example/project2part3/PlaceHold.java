package com.example.project2part3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import	android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private static Boolean confirmCheckOut(String checkout, String checkin){
        String pattern = "MM/dd/yyyy HH:mm";

        Date dateDay1;
        Date dateDay2;
        Date dummyDate;

        try{
            dateDay1 = new SimpleDateFormat(pattern).parse(checkout);
            dateDay2 = new SimpleDateFormat(pattern).parse(checkin);
            dummyDate = new SimpleDateFormat(pattern).parse(checkout);
        }catch(Exception e){
            return false;
        }

        SimpleDateFormat tempPattern = new SimpleDateFormat("MM/dd/yy");
        Date tempDate;

        try{
            tempDate = tempPattern.parse(dateDay1.getMonth() + "/" + dateDay1.getDay()+"/" + dateDay1.getYear());
        }catch (Exception e){
            return false;
        }


        Calendar c = Calendar.getInstance();
        String temp = tempDate.toString();
        try{
            //Setting the date to the given date
            c.setTime(tempPattern.parse(temp));
        }catch(ParseException e){
            e.printStackTrace();
        }


        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 7);

//        dummyDate.setMonth();
        try{
            dummyDate.setMonth(Integer.parseInt(new SimpleDateFormat("MM").format(c.getTime())));
            dummyDate.setDate(Integer.parseInt(new SimpleDateFormat("dd").format(c.getTime())));
            dummyDate.setYear(Integer.parseInt(new SimpleDateFormat("yyyy").format(c.getTime())));
        }catch (Exception e){
            return false;
        }

        if(dateDay2.after(dummyDate)){
            return false;
        }
        return true;
    }
}
