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

public class ManageSystem extends AppCompatActivity {

    private DataBase db;
    private EditText username, password;
    private Button loginButton;
    private TextView records;
    private Button doneRecordsButton;
    private EditText title, author, fee;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);

        db = new DataBase(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton =(Button) findViewById(R.id.loginButton);
        records = (TextView) findViewById(R.id.records);
        doneRecordsButton = (Button) findViewById(R.id.doneRecordsButton);
        title = (EditText) findViewById(R.id.title);
        author = (EditText) findViewById(R.id.author);
        fee = (EditText) findViewById(R.id.author);
        addButton = (Button) findViewById(R.id.addButton);

        doneRecordsButton.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        author.setVisibility(View.INVISIBLE);
        fee.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = username.getText().toString();
                String p = password.getText().toString();

                if( !u.equals("Admin2") || !p.equals("Admin2")){
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageSystem.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Information in valid");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                    return;
                }


                String info = db.getManageInfo();

                records.setText(info);
                Log.d("l",info);

                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder( ManageSystem.this );
                builder1.setMessage("Add a book");
                builder1.setCancelable(true);

                doneRecordsButton.setVisibility(View.VISIBLE);


            }
        });

        doneRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder( ManageSystem.this );
                builder1.setTitle("");
                builder1.setMessage("Add book");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                doneRecordsButton.setVisibility(View.VISIBLE);
                                title.setVisibility(View.VISIBLE);
                                author.setVisibility(View.VISIBLE);
                                fee.setVisibility(View.VISIBLE);
                                addButton.setVisibility(View.VISIBLE);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(ManageSystem.this, MainActivity.class));
                            }
                        });

                android.app.AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String a = author.getText().toString();
                String f = fee.getText().toString();

                if (t.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageSystem.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Title is empty");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                }
                else if(a.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageSystem.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Author is empty");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                }
                else if(t.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageSystem.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Fee is empty");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                }

                if ( db.getBookInfo(t) != null ){
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageSystem.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Book is already listed");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });

                    alertDialog.show();
                }

                db.addBookDB(t, a, Double.parseDouble(f));
            }
        });
    }
}
