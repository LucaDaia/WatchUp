package com.example.watchup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphActivity extends AppCompatActivity implements OnChartValueSelectedListener, FetchPersonDataCallback {

    List<Image> imageList = new ArrayList<>();
    List<PersonForGraph> listOfPersons = new ArrayList<>();
    List<Person> personListFetcher = new ArrayList<>();

    BarChart barChart;
    PieChart pieChart;
    BarChart barChartHours;
    TextView textViewNumber;
    TextView textViewVisitor;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        imageList = getIntent().getParcelableArrayListExtra("imageList");
        System.out.println(imageList);

        barChart = findViewById(R.id.barChart);
        pieChart = findViewById(R.id.pieChart);
        barChartHours = findViewById(R.id.barChartHours);

        barChart.setOnChartValueSelectedListener(this);

        GraphUtils graphUtils = new GraphUtils(imageList,getApplicationContext());
        this.listOfPersons = graphUtils.getListOfPersons();

        graphUtils.configureBarChart(barChart, this.listOfPersons);
        graphUtils.configurePieChart(pieChart, this.listOfPersons);
        graphUtils.configureBarChartHours(barChartHours);

        textViewNumber = findViewById(R.id.totalNumberOfVisitsTV);
        textViewVisitor = findViewById(R.id.mostActivePersonTV);

        String usefulString = "Total number of visits: " + graphUtils.totalNumberOfVisits(this.listOfPersons);
        textViewNumber.setText(usefulString);
        String stringForText = "Most active person: " + graphUtils.getMostActiveVisitor(this.listOfPersons).getName() +
                " Number of visits: " + graphUtils.getMostActiveVisitor(this.listOfPersons).getNumberOfVisits();
        textViewVisitor.setText(stringForText);

        System.out.println("LISTAAAA OREEE HAUUU");
        System.out.println(graphUtils.getHourlyVisits());

        OnChartValueSelectedListener onChartValueSelectedListener = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                String label = pieEntry.getLabel();
                Float value = pieEntry.getValue();

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View dialogView = inflater.inflate(R.layout.barchart_info, null);

                TextView nameTV = dialogView.findViewById(R.id.textViewNameBarChartDetails);
                String s = "Name: " + label;
                nameTV.setText(s);
                TextView visitsTV = dialogView.findViewById(R.id.textViewVisitsBarChartDetails);
                visitsTV.setText("Number of visits: " + value.intValue());
                Person p = Utils.findPerson(GraphActivity.this.personListFetcher, label);
                System.out.println(p);
                imgView = dialogView.findViewById(R.id.imageViewBarChartDetails);
                if (p.getName().equals("Unknown")) {
                    Utils.insertUnknown(imgView);
                } else {
                    Utils.insertImageByName(Utils.removePersonImagesPath(p.getImgData()), imgView, false);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                builder.setView(dialogView);

                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onNothingSelected() {

            }
        };

        pieChart.setOnChartValueSelectedListener(onChartValueSelectedListener);

    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Float xValue = e.getX();
        Float yValue = e.getY();
        System.out.println("ACESTA ESTE THE SNAKEEE " + this.listOfPersons.get(xValue.intValue()));

        // Inflate the custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.barchart_info, null);

        // Set the x value information in the dialog
        TextView nameTV = dialogView.findViewById(R.id.textViewNameBarChartDetails);
        String s = "Name: " + this.listOfPersons.get(xValue.intValue()).getName();
        nameTV.setText(s);
        TextView visitsTV = dialogView.findViewById(R.id.textViewVisitsBarChartDetails);
        visitsTV.setText("Number of visits: " + yValue.intValue());
        Person p = Utils.findPerson(this.personListFetcher, this.listOfPersons.get(xValue.intValue()).getName());
        System.out.println(p);
        imgView = dialogView.findViewById(R.id.imageViewBarChartDetails);
        if(p.getName().equals("Unknown")) {
            Utils.insertUnknown(imgView);
        }
        else {
            Utils.insertImageByName(Utils.removePersonImagesPath(p.getImgData()), imgView, false);
        }

        BackendFetcher fetcherPersons = new BackendFetcher();
        fetcherPersons.fetchPersonData(this);

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPersonSuccess(List<Person> personList) {
        System.out.println(personList);
        this.personListFetcher = personList;
    }

    @Override
    public void onPersonFailure(String errorMessage) {

    }
}