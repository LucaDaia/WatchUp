package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {

    List<Image> imageList = new ArrayList<>();

    BarChart barChart;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        imageList = getIntent().getParcelableArrayListExtra("imageList");
        System.out.println(imageList);

        barChart = findViewById(R.id.barChart);
        pieChart = findViewById(R.id.pieChart);


        GraphUtils graphUtils = new GraphUtils(imageList,getApplicationContext());
        List<PersonForGraph> listOfPersons = graphUtils.getListOfPersons();

        graphUtils.configureBarChart(barChart, listOfPersons);
        graphUtils.configurePieChart(pieChart, listOfPersons);
    }
}