package com.example.watchup;

import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String BaseUrl = "http://10.0.2.2:8081/api/";

    public static void insertImageByName(String name, ImageView imageView) {
        String url = Utils.BaseUrl + "images/name/" + name;
        Picasso.get()
                .load(url)
                .into(imageView);
    }


    public static String formatDateTime(String dateTimeString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateTimeString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String removeImagesPath(String input) {
        String substring = "Images\\";
        int startIndex = input.indexOf(substring);
        if (startIndex != -1) {
            startIndex += substring.length();
            return input.substring(startIndex);
        } else {
            return ""; // Return an empty string if "Images\" is not found
        }
    }
}



