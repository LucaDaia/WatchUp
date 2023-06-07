package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;

public class ImageDownloadPage extends AppCompatActivity {

    public ImageView imgView;
    public Button btnDownload;
    public Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download_page);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        String name = intent.getStringExtra("name");
        System.out.println(data);

        imgView = findViewById(R.id.imageViewForActivity);
        btnDownload = findViewById(R.id.butonDownload);
        btnShare = findViewById(R.id.butonShare);


        Utils.insertImageByName(Utils.removeImagesPath(data), imgView, true);

        btnDownload.setOnClickListener(view -> {
            ImageSaver imageSaver = new ImageSaver(this.getApplicationContext());
            imageSaver.saveImageToGallery(imgView);
        });

        btnShare.setOnClickListener(view -> {
            ImageShare imageShare = new ImageShare(this);
            imageShare.shareImage(imgView);
        });
    }


}