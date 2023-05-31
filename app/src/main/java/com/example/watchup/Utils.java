package com.example.watchup;

import android.text.BoringLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {
//    public static String BaseUrl = "http://10.0.2.2:8081/api/";
      public static String BaseUrl = "https://2b43-188-25-244-211.ngrok-free.app/api/";

    public static void insertImageByName(String name, ImageView imageView, Boolean typeOfImage) {
       String url = "";
        if(typeOfImage == true) {
            url = Utils.BaseUrl + "images/name/" + name;
       }
       else {
           url = Utils.BaseUrl + "persons/name/" + name;
       }
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

    public static String removePersonImagesPath(String input) {
        String substring = "Persons\\";
        int startIndex = input.indexOf(substring);
        if(startIndex != -1) {
            startIndex += substring.length();
            return input.substring(startIndex);
        } else {
            return "";
        }
    }


    public static void sortObjectsByDate(List<Image> objects) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Collections.sort(objects, new Comparator<Image>() {
            @Override
            public int compare(Image obj1, Image obj2) {
                String dateStr1 = obj1.getCreatedAt();
                String dateStr2 = obj2.getCreatedAt();

                LocalDateTime date1 = LocalDateTime.parse(dateStr1, formatter);
                LocalDateTime date2 = LocalDateTime.parse(dateStr2, formatter);

                return date2.compareTo(date1);
            }
        });
    }
}



