package com.example.project2part3;

public class Reserv {

    private String username;
    private String book;
    private String pickupTime;
    private String returnTime;
    private String num;
    private double total;

    public Reserv(String username, String book, String pickupTime, String returnTime, String num, double total) {
        this.username = username;
        this.book = book;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.num = num;
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
