package com.example.project2part3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Button createAccountButton;
    private Button holdButton;
    private Button canelHoldButton;
    private Button manageSystemButton;
    private Button placeHoldButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        canelHoldButton = (Button) findViewById(R.id.cancelHoldButton);
        manageSystemButton = (Button) findViewById(R.id.manageSystemButton);
        placeHoldButton = (Button) findViewById(R.id.placeHoldButton);
    };

        public void users_activity(View v){
            startActivity(new Intent(MainActivity.this, CreateActivity.class));
        }
        public void manageSystem_activity(View v){
            startActivity(new Intent(MainActivity.this, ManageSystem.class));
        }

        public void hold_activity(View v){
            startActivity(new Intent(MainActivity.this, PlaceHold.class));
        }

        public void cancelHold_activity(View v){
            startActivity(new Intent(MainActivity.this, CancelHold.class));
        }

}