package com.example.watchup;

public class Image {
    private int id;
    private String data;
    private String name;
    private int userID;
    private String createdAt;

    public Image(int id, String data, String name, int userID, String createdAt) {
        this.id=id;
        this.data=data;
        this.name = name;
        this.userID=userID;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getData() {
        return data;
    }

//    public void setData(String data) {
//        this.data = data;
//    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public int getUserID() {
        return userID;
    }

//    public void setUserID(int userID) {
//        this.userID = userID;
//    }

    public String getCreatedAt() {
        return this.createdAt;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", name='" + name + '\'' +
                ", userID=" + userID +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
