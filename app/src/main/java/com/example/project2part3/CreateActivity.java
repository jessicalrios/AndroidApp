package com.example.project2part3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

    private TextView createUsernameTextView;
    private EditText getUsernameEditText;
    private TextView createPasswordTextView;
    private EditText getPasswordEditText;
    private Button createAccSubmit;

    private TextView accountSuccessTextView;
    private TextView accountFailTextView;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = new DataBase(this);

        createUsernameTextView = findViewById(R.id.createUsernameTextView);
        getUsernameEditText = findViewById(R.id.getUsernameEditText);
        createPasswordTextView = findViewById(R.id.createPasswordTextView);
        getPasswordEditText = findViewById(R.id.getPasswordEditText);
        createAccSubmit = findViewById(R.id.createAccSubmit);

        accountSuccessTextView = findViewById(R.id.accountSuccessTextView);
        accountFailTextView = findViewById(R.id.accountFailTextView);

        createAccSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                String userName = getUsernameEditText.getText().toString();
                String password = getPasswordEditText.getText().toString();

                if(createAccount(userName) && createAccount(password) && db.addUsersDB(new Users(userName, password))) {
                    accountSuccessTextView.setVisibility(v.VISIBLE);

                } else
                    accountFailTextView.setVisibility(v.VISIBLE);
            }
        });


    }

    Boolean createAccount(String name){

        int totalNumber=0, totalChar = 0;

        if(name.length() > 8 || name.length() < 4)
            return false;

        for(int i=0; i<name.length(); i++){

            if(name.charAt(i) >= '0' && name.charAt(i) <= '9')
                totalNumber++;
            else if((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))
                totalChar++;
            else
                return false;
        }

        if(name.equals("Admin2"))
            return false;

        return(totalChar >= 3 && totalNumber >= 1);
    }

}
