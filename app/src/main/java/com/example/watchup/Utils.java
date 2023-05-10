package com.example.watchup;

import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Utils {
    public static String BaseUrl = "http://10.0.2.2:8081/api/";

    public static void insertImageByName(String name, ImageView imageView) {
        String url =  Utils.BaseUrl + "images/name/"+ name;
        Picasso.get()
                .load(url)
                .into(imageView);
    }




}
