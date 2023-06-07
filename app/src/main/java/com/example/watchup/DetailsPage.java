package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class DetailsPage extends AppCompatActivity implements FetchPersonDataCallback, RecyclerViewInterface{

    private ImageView imgView;
    private RecyclerView recyclerViewDetails;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textViewName;
    private TextView textViewDetails;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Intent intent = getIntent();
        if(intent.hasExtra("name")) {
            this.name = intent.getStringExtra("name");
        }
        System.out.println("LLLLLLLLLLLLLLLLLLaaaaaaa");
        System.out.println(name);

        List<Image> imageList = intent.getParcelableArrayListExtra("imageList");
        System.out.println("URMEAZAAAA LISTA CU IMAGINI SIUUUUU");
        System.out.println(imageList);
        List<Image> listForName = Utils.sortImageListOnlyByName(imageList, this.name);

        imgView = findViewById(R.id.imageViewDetails);
        textViewName=findViewById(R.id.textViewName);
        textViewDetails=findViewById(R.id.textViewDetails);

        BackendFetcher fetcherPersons = new BackendFetcher();
        fetcherPersons.fetchPersonData(this);


        recyclerViewDetails = findViewById(R.id.recyclerViewDetails);
        recyclerViewDetails.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewDetails.setLayoutManager(layoutManager);
        adapter = new ImageAdapter(this, listForName);
        recyclerViewDetails.setAdapter(adapter);
    }

    @Override
    public void onPersonSuccess(List<Person> personList) {
        System.out.println(personList);
        Person personChoosed = new Person();
        personChoosed.setName(this.name);
        for(Person p : personList) {
            System.out.println(p.getName());
            if(p.getName().equals(this.name)) {
                System.out.println(Utils.removeImagesPath(p.getImgData()));
                personChoosed.setName(p.getName());
                personChoosed.setDetails(p.getDetails());
                personChoosed.setImgData(p.getImgData());
            }
        }

        Utils.insertImageByName(Utils.removePersonImagesPath(personChoosed.getImgData()), imgView, false);
        this.textViewDetails.setText(personChoosed.getDetails());
        this.textViewName.setText(personChoosed.getName());
    }

    @Override
    public void onPersonFailure(String errorMessage) {
        System.out.println("Can't find the persons list");
    }

    @Override
    public void onItemClick(int position, String name, String data) {
        Intent intent = new Intent(this.getApplicationContext(), ImageDownloadPage.class);
        intent.putExtra("data", data);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}