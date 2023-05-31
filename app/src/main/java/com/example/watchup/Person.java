package com.example.watchup;

public class Person {
    private String name;
    private String details;
    private String imgData;

    public Person(String name, String details, String imgData) {
        this.name = name;
        this.details = details;
        this.imgData = imgData;
    }

    public Person() {
        this.name = "Unknown";
        this.details = "Unknown";
        this.imgData = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImgData() {
        return imgData;
    }

    public void setImgData(String imgData) {
        this.imgData = imgData;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", imgData='" + imgData + '\'' +
                '}';
    }
}
