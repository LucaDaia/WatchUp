package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondPage extends AppCompatActivity implements FetchPersonDataCallback{
    private ImageView imageView;
    private BackendFetcher fetcher;


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
        Utils.insertImageByName("1682190407047.jpg", imageView,true);
//        fetcher.fetchData();
//        List<Image> listuta= fetcher.getImageList();
//        System.out.println(fetcher.getImageList());
//        System.out.println(listuta);
        //insertImageByName("1682190407047.jpg", imageView);
        String proba2 = "Images\1678117876881.jpg";
        Toast.makeText(this, Utils.removeImagesPath(proba2), Toast.LENGTH_LONG).show();

        fetcher = new BackendFetcher();
        fetchData();

    }

    private void fetchData() {
        fetcher.fetchPersonData(this);
    }

    @Override
    public void onPersonSuccess(List<Person> personList) {
        System.out.println("SAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(personList);
    }

    @Override
    public void onPersonFailure(String errorMessage) {
        System.out.println("Ghinion vere");
    }
}
