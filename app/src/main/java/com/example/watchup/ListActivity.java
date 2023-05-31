package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements FetchDataCallback/*,FetchPersonDataCallback*/, RecyclerViewInterface{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    // ------
    private BackendFetcher fetcher;
    private Handler handler;
    private Runnable fetchRunnable;
    private List<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        BackendFetcher fetcher = new BackendFetcher();
//        fetcher.fetchData(this);

        fetcher = new BackendFetcher();
        handler = new Handler();

//        fetcher.fetchPersonData(this);
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
        System.out.println("Reapelare fetcher");

        Utils.sortObjectsByDate(imageList);
        adapter = new ImageAdapter(this, imageList);
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

    @Override
    public void onItemClick(int position, String name) {

        System.out.println("SA VEDEM CE O SA IASA");
        System.out.println("Position: " + position);
        System.out.println("Name: " + name);

        Intent intent = new Intent(this.getApplicationContext(), DetailsPage.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

//    @Override
//    public void onPersonSuccess(List<Person> personList) {
//        this.personList = personList;
//        System.out.println("PERSOOOOOOOOOOOOOOOOON");
//        System.out.println(this.personList);
//    }
//
//    @Override
//    public void onPersonFailure(String errorMessage) {
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
//    }
}
