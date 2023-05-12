package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity /* implements FetchDataCallback */{

    Button btnGetStarted;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStarted = findViewById(R.id.butonGetStarted);
        btnGetStarted.setOnClickListener(view -> {
            Intent it = new Intent(this.getApplicationContext(), SecondPage.class);
            startActivity(it);
        });

        btnTest = findViewById(R.id.butonTestList);
        btnTest.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplicationContext(), ListActivity.class);
            startActivity(intent);
        });

//        BackendFetcher fetcher = new BackendFetcher();
//        fetcher.fetchData(this);
    }
//
//    @Override
//    public void onSuccess(List<Image> imageList) {
//        // Handle successful data retrieval
//        List<Image> listuta = imageList;
//        Image img = listuta.get(1);
//        System.out.println("Okkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
//        System.out.println(img.toString());
//        // Use the fetched data as needed
//    }
//
//    @Override
//    public void onFailure(String errorMessage) {
//        // Handle failure
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
//    }
}