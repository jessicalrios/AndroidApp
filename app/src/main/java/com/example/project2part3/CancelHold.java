package com.example.project2part3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CancelHold extends AppCompatActivity {

    private DataBase db;
    private EditText username, password;
    private TextView holds;
    private Button searchButton;
    private EditText removeText;
    private Button removeButton;
    private ArrayList<Reserv> reservations;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold);

        db = new DataBase(this);

        index = 0;
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        holds = (TextView) findViewById(R.id.holds);
        searchButton = (Button) findViewById(R.id.search);
        removeText = (EditText) findViewById(R.id.removeBookText);
        removeButton = (Button) findViewById(R.id.removeButton);

        reservations = new ArrayList<>();

        removeButton.setVisibility(View.INVISIBLE);
        removeText.setVisibility(View.INVISIBLE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !db.getUser( username.getText().toString(), password.getText().toString()) ){

                    AlertDialog alertDialog = new AlertDialog.Builder(CancelHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Invalid information");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                    return;
                }

                reservations = db.getHolds( username.getText().toString());

                if(reservations.isEmpty()){

                    AlertDialog alertDialog = new AlertDialog.Builder(CancelHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("The account has no holds");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(CancelHold.this, MainActivity.class));
                        }
                    });

                    alertDialog.show();
                    return;
                }

                String r = "";

                for(int i=0; i<reservations.size(); i++){

                    r += reservations.get(i).getUsername() + "\n" + reservations.get(i).getBook() + "\n" + reservations.get(i).getPickupTime() + "\nReservation number: " + reservations.get(i).getReturnTime() + reservations.get(i).getNum() + "\n\n";
                }

                holds.setText(r);
                removeButton.setVisibility(View.VISIBLE);
                removeText.setVisibility(View.VISIBLE);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String r = removeText.getText().toString();
                for(int i=0; i<reservations.size(); i++){

                    if(reservations.get(i).getNum().equals(r)){

                        index = i;

                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder( CancelHold.this );
                        builder1.setTitle("Confirm Cancelation");
                        builder1.setMessage(reservations.get(i).getBook() + "\n" + reservations.get(i).getReturnTime() + "\n" + reservations.get(i).getReturnTime() + "\n" + reservations.get(i) );
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        db.removeReservation(reservations.get(index));

                                        startActivity(new Intent(CancelHold.this, MainActivity.class));
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });

                        android.app.AlertDialog alert11 = builder1.create();
                        alert11.show();


                        return;
                    }
                }


            }
        });


    }
}
