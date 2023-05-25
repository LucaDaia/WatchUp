package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements FetchDataCallback{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    // ------
    private BackendFetcher fetcher;
    private Handler handler;
    private Runnable fetchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        BackendFetcher fetcher = new BackendFetcher();
//        fetcher.fetchData(this);

        fetcher = new BackendFetcher();
        handler = new Handler();

        fetchData();

        fetchRunnable = new Runnable() {
            @Override
            public void run() {
                // Fetch data every ten seconds
                fetchData();
                // Schedule the next execution after 10 seconds
                handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(fetchRunnable,40000);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        }

        private void fetchData() {
        fetcher.fetchData(this);
        }


    @Override
    public void onSuccess(List<Image> imageList) {
        // Handle successful data retrieval
        List<Image> listuta = imageList;
        Image img = listuta.get(0);
        System.out.println("Okkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        System.out.println(img.toString());

        Utils.sortObjectsByDate(imageList);
        adapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(adapter);
        // Use the fetched data as needed

    }

    @Override
    public void onFailure(String errorMessage) {
        // Handle failure
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop fetching data when the activity is destroyed
        handler.removeCallbacks(fetchRunnable);
    }
    }
