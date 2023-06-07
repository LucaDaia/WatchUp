package com.example.watchup;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Image implements Parcelable {
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

    protected Image(Parcel in) {
        id = in.readInt();
        data = in.readString();
        name = in.readString();
        userID = in.readInt();
        createdAt = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(data);
        dest.writeString(name);
        dest.writeInt(userID);
        dest.writeString(createdAt);
    }
}
