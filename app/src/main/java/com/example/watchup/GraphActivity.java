package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GraphActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

//        bottomNavigationView = findViewById(R.i);
//        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
//
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            if(item.getItemId() == R.id.bottom_home) {
//                return true;
//            }
//            else if(item.getItemId() == R.id.bottom_settings) {
//                //start settings activity
//                return true;
//            }
//            else if(item.getItemId() == R.id.bottom_graph) {
//                //start graphActivity
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                return true;
//            }
//            else if(item.getItemId() == R.id.bottom_logout) {
//                return true;
//            }
//            return false;
//
//        });
    }
}