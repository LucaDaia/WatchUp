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

public class MainActivity extends AppCompatActivity /* implements FetchDataCallback */ {

    Button btnGetStarted;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.checkNotifPermission(MainActivity.this, MainActivity.this);

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

        Intent serviceIntent = new Intent(this, DataFetchService.class);
        startService(serviceIntent);

    }
}