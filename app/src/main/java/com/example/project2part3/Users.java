package com.example.project2part3;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Users {

//    private int id;
    private String name;
    private String password;

    public Users(){
//        id=0;
        name = "";
        password = "";
//        date = Calendar.getInstance().getTime();
    }

    public Users(String name, String password){
        this.name = name;
        this.password = password;
//        getDate();
    }


    public String getUserName(){
        return name;
    }

    public void setUserName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String name){
        this.name = name;
    }

//    public String getDate(){
//        date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(date);
//        return strDate;
//
//    }




}
