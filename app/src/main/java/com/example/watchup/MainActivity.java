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

public class MainActivity extends AppCompatActivity {

    Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStarted = findViewById(R.id.butonGetStarted);
        btnGetStarted.setOnClickListener(view -> {
            //Toast.makeText(this.getApplicationContext(),"Sarpili haide", Toast.LENGTH_LONG).show();
            Intent it = new Intent(this.getApplicationContext(), SecondPage.class);
            startActivity(it);
        });



//
    }
}