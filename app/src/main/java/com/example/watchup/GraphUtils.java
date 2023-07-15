package com.example.watchup;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphUtils {
    private List<Image> listOfImages;
    private Context context;

    public GraphUtils(List<Image> listOfImages, Context context) {
        this.listOfImages = listOfImages;
        this.context=context;
    }

    public List<PersonForGraph> getListOfPersons() {
        List<PersonForGraph> listOfPersons = new ArrayList<>();
        for(Image img: listOfImages) {
            boolean ok=false;
            for(PersonForGraph prs: listOfPersons) {
                if(img.getName().equals(prs.getName())) {
                    prs.setNumberOfVisits(prs.getNumberOfVisits()+1);
                    ok=true;
                }
            }
            if(ok==false) {
                listOfPersons.add(new PersonForGraph(img.getName(), 1));
            }
        }
        return listOfPersons;
    }

    public void configureBarChart(BarChart barChart, List<PersonForGraph> listOfPersons) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < listOfPersons.size(); i++) {
            float xValue = i;
            float yValue = listOfPersons.get(i).getNumberOfVisits();
            String label = listOfPersons.get(i).getName();

            barEntries.add(new BarEntry(xValue, yValue, label));
        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "Visitors BarGraph");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);


        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisLeft.setGranularity(1f);
        XAxis xAxis = barChart.getXAxis();
//        xAxis.setGranularity(0.5f); // Set the spacing between each label
//        xAxis.setGranularityEnabled(true); // Enable the custom spacing

        xAxis.setDrawLabels(true);
        xAxis.setLabelRotationAngle(45f); // Rotate the labels by 45 degrees for better visibility
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position the labels at the bottom of the chart
        xAxis.setDrawGridLines(false); // Hide the vertical grid lines (optional)
//        xAxis.setAxisMinimum(-0.5f); // Adjust the minimum value to avoid cutting off the first label
//        xAxis.setAxisMaximum(listOfPersons.size() - 0.5f); // Adjust the maximum value to avoid cutting off the last label

        barChart.getXAxis().setAxisLineWidth(2f);


        barChart.setData(new BarData(barDataSet));
        barChart.animateXY(1000, 1000);
        barChart.getDescription().setEnabled(false);
    }


    public void configurePieChart(PieChart pieChart, List<PersonForGraph> listOfPersons) {
        System.out.println("LIST OF PERSONS SOUTTTTTT");
        System.out.println(listOfPersons);

        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < listOfPersons.size(); i++) {
            labels.add(listOfPersons.get(i).getName());
        }

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int j = 0; j < listOfPersons.size(); j++) {
            float xValue = j;
            String label = labels.get(j);
            float numberOfVisits = listOfPersons.get(j).getNumberOfVisits();
            pieEntries.add(new PieEntry(numberOfVisits, label));
//            System.out.println(j);
//            System.out.println(listOfPersons.get(j).getNumberOfVisits());
        }
        System.out.println(pieEntries);


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Visitors PieChart");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(3f); // Add some space between the slices

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(12f); // Set the size of the label text

//// Set the custom ValueFormatter for the labels
//        pieData.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                // Return the label corresponding to the value
//                int index = (int) value;
//                if (index >= 0 && index < labels.size()) {
//                    return labels.get(index);
//                }
//                return ""; // Return empty string for invalid indices
//            }
//        });

        //change the color in the middle
        int holeColor = ContextCompat.getColor(context, R.color.white_yellow);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(holeColor);

        pieChart.setData(pieData);
        pieChart.animateXY(2000,2000);
        pieChart.getDescription().setEnabled(false);
    }

    public int totalNumberOfVisits(List<PersonForGraph> personList) {
        int sum = 0;
        for(PersonForGraph p: personList) {
            sum+=p.getNumberOfVisits();
        }
        return sum;
    }

    public PersonForGraph getMostActiveVisitor(List<PersonForGraph> personList) {
        PersonForGraph person = personList.get(0);
        for(PersonForGraph p : personList) {
            if(p.getNumberOfVisits() > person.getNumberOfVisits()) {
                person = p;
            }
        }
        return person;
    }

    public List<Integer> getHourlyVisits() {
        List<Integer> hoursList = new ArrayList<>();
        hoursList.add(0,0);
        hoursList.add(1,0);
        hoursList.add(2,0);
        hoursList.add(3,0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime date;
        for(Image i: this.listOfImages) {
            date = LocalDateTime.parse(i.getCreatedAt(), formatter);
            int hour = date.getHour();
            if(hour>=0 && hour<6) {
                int currentValueNr = hoursList.get(0);
                hoursList.remove(0);
                hoursList.add(0, currentValueNr+1);
            }
            else if(hour>=6 && hour<12) {
                int currentValueNr = hoursList.get(1);
                hoursList.remove(1);
                hoursList.add(1, currentValueNr+1);
            }
            else if(hour>=12 && hour<18) {
                int currentValueNr = hoursList.get(2);
                hoursList.remove(2);
                hoursList.add(2, currentValueNr+1);
            }
            else if(hour>=18 && hour<24) {
                int currentValueNr = hoursList.get(3);
                hoursList.remove(3);
                hoursList.add(3, currentValueNr+1);
            }
        }
        return hoursList;
    }


    public void configureBarChartHours(BarChart barChart) {
        List<Integer> hoursList = this.getHourlyVisits();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < hoursList.size(); i++) {
            float xValue = i;
            float yValue = hoursList.get(i);
            String label;
            switch(i) {
                case 0:
                    label="Night";
                    break;
                case 1:
                    label="Morning";
                    break;
                case 2:
                    label="Noon";
                    break;
                case 3:
                    label="Evening";
                    break;
                default: label="Unknown";
            }

            barEntries.add(new BarEntry(xValue, yValue, label));
        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "Visitors Per Hour BarGraph");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < hoursList.size()) {
                    switch(index) {
                        case 0:
                            return "Night";
                        case 1:
                            return "Morning";
                        case 2:
                            return "Noon";
                        case 3:
                            return "Evening";
                        default: return "Unknown";
                    }
                }
                return "";
            }
        });

//        xAxis.setGranularity(2f); // Set the spacing between each label
        xAxis.setGranularityEnabled(true); // Enable the custom spacing

//        xAxis.setLabelRotationAngle(45f); // Rotate the labels by 45 degrees for better visibility
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position the labels at the bottom of the chart
        xAxis.setDrawGridLines(false); // Hide the vertical grid lines (optional)
        xAxis.setAxisMinimum(-0.5f); // Adjust the minimum value to avoid cutting off the first label
        xAxis.setAxisMaximum(hoursList.size() - 0.5f); // Adjust the maximum value to avoid cutting off the last label

        barChart.getXAxis().setAxisLineWidth(2f);


        barChart.setData(new BarData(barDataSet));
        barChart.animateXY(1000, 1000);
        barChart.getDescription().setEnabled(false);
    }
}
