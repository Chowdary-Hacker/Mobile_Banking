package com.example.uffdcbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity {
    Button transfer, viewBalance, history;
    PieChart anyChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        anyChartView = findViewById(R.id.any_chart_view);
        // Sample data for the pie chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, "Entry 1"));
        entries.add(new PieEntry(70f, "Entry 2"));

        // Create a dataset with data and label
        PieDataSet dataSet = new PieDataSet(entries, "Sample Pie Chart");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Create a pie data object and set it to the chart
        PieData pieData = new PieData(dataSet);
        anyChartView.setData(pieData);

        // Customize the chart
        anyChartView.getDescription().setEnabled(false);
        anyChartView.setHoleRadius(25f);
        anyChartView.setTransparentCircleRadius(30f);

        // Refresh the chart
        anyChartView.invalidate();

        Intent intentt = getIntent();
        String user = intentt.getStringExtra("username");
        transfer = findViewById(R.id.btnTransferMoney);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select the user(account holder)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), select.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish();
            }
        });

        //view balance
        viewBalance = findViewById(R.id.btnViewBalance);
        viewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), ":)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ViewBalance.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });

        //transaction history
        history = findViewById(R.id.btnTransactionHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Look at your transaction history :)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), History.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });
    }}
