package com.example.project2part3;

public class Book {

    private String Title;
    private String Author;
    private double fee;

    public Book(String title, String author, double fee) {
        Title = title;
        Author = author;
        this.fee = fee;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
