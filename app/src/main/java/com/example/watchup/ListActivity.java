package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import java.io.Serializable;
import java.sql.Array;
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
    private List<Image> imageListForSending = new ArrayList<>();
    //-----
    FirebaseAuth auth;
    FirebaseUser user;
    //----
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        auth  = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationViewLA);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.bottom_home) {
                return true;
            }
            else if(item.getItemId() == R.id.bottom_settings) {
                //start settings activity
                return true;
            }
            else if(item.getItemId() == R.id.bottom_graph) {
                //start graphActivity
                Intent i = new Intent(getApplicationContext(), GraphActivity.class);
                i.putParcelableArrayListExtra("imageList", (ArrayList<? extends Parcelable>) this.imageListForSending);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if(item.getItemId() == R.id.bottom_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;

        });

//        BackendFetcher fetcher = new BackendFetcher();
//        fetcher.fetchData(this);

        fetcher = new BackendFetcher();
        handler = new Handler();

//        fetcher.fetchPersonData(this);
        fetchData();

        fetchRunnable = new Runnable() {
            @Override
            public void run() {
                fetchData();

                handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(fetchRunnable,10000);

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
        this.imageListForSending = imageList;
        System.out.println("Reapelare fetcher");

        Utils.sortObjectsByDate(imageList);
        adapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(adapter);

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
    public void onItemClick(int position, String name, String data) {

        System.out.println("SA VEDEM CE O SA IASA");
        System.out.println("Position: " + position);
        System.out.println("Name: " + name);

        Intent intent = new Intent(this.getApplicationContext(), DetailsPage.class);
        intent.putExtra("name", name);
        intent.putExtra("imageList", (Serializable) this.imageListForSending);
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
