package com.example.watchup;

public class Image {
    private int id;
    private String data;
    private String name;
    private int userID;

    public Image(int id, String data, String name, int userID) {
        this.id=id;
        this.data=data;
        this.name = name;
        this.userID=userID;
    }
}
