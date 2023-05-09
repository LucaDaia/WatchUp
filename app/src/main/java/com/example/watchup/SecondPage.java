package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondPage extends AppCompatActivity {
    private ImageView imageView;

    private void insertImageByName(String name, ImageView imageView) {
        String url = "http://10.0.2.2:8081/api/images/name/" + name;
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        imageView = findViewById(R.id.image_view);

//        Picasso.get()
//                .load("http://10.0.2.2:8081/api/images/name/1682190407047.jpg")
//                .into(imageView);
        Utils.insertImageByName("1682190407047.jpg", imageView);
        //insertImageByName("1682190407047.jpg", imageView);
    }
}
