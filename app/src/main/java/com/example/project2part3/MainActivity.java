package com.example.project2part3;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button createAccountButton;
    private Button createAccSubmit;
    private Button cancelHoldButton;
    private Button manageSystemButton;
    private Button placeHoldButton;
    private TextView createUsernameTextView;
    private EditText getUsernameEditText;
    private TextView createPasswordTextView;
    private EditText getPasswordEditText;
    private TextView accountSuccessTextView;
    private TextView accountFailTextView;
    private Users users;

    private DataBase dbUsers;
//    private DataBase dbBooks;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbUsers = new DataBase(this);
//        dbBooks = new DataBase(this);

        createAccountButton = findViewById(R.id.createAccountButton);
        cancelHoldButton = findViewById(R.id.cancelHoldButton);
        manageSystemButton = findViewById(R.id.manageSystemButton);
        placeHoldButton = findViewById(R.id.placeHoldButton);

        dbUsers.addUsersDB(new Users("Admin2", "Admin2"));
        dbUsers.addUsersDB(new Users("alice5", "csumb100"));
        dbUsers.addUsersDB(new Users("Brian7", "123abc"));
        dbUsers.addUsersDB(new Users("chris12", "CHRIS"));

        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.createactivity_main);

                createUsernameTextView = findViewById(R.id.createUsernameTextView);
                getUsernameEditText = findViewById(R.id.getUsernameEditText);
                createPasswordTextView = findViewById(R.id.createPasswordTextView);
                getPasswordEditText = findViewById(R.id.getPasswordEditText);
                createAccSubmit = findViewById(R.id.createAccSubmit);
                accountSuccessTextView = findViewById(R.id.accountSuccessTextView);
                accountFailTextView = findViewById(R.id.accountSuccessTextView);

                createAccountButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v){

                        String userName = getUsernameEditText.getText().toString();
                        String password = getPasswordEditText.getText().toString();

                        if(createAccount(userName) && createAccount(password)) {
                            accountSuccessTextView.setVisibility(v.VISIBLE);
                            dbUsers.addUsersDB(new Users(userName, password));
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
                    if(name.charAt(i) >= 'a' && name.charAt(i) <= 'z' || name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')
                        totalChar++;
                    else
                        return false;
                }

                if(name.equals("Admin2"))
                    return false;

                return(totalChar >= 3 && totalNumber >= 1);
            }
        });
    }
}
