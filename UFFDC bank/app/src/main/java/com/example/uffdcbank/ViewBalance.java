package com.example.uffdcbank;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import android.os.Bundle;

import com.google.gson.Gson;

public class ViewBalance extends AppCompatActivity {
 int responseCode ;  StringBuilder result = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbalance);
        TextView balanceAmountTextView = findViewById(R.id.available_balance_amount);
        Intent intentt = getIntent();
        String user = intentt.getStringExtra("username");
        new Thread(() -> {
            try {
                String apiUrl = "http://13.232.31.31:3334/viewBalance/"+user;
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                responseCode = connection.getResponseCode();
                runOnUiThread(() -> {
                    if(responseCode == 201){
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while (true) {
                                if ((line = reader.readLine()) == null) break;
                            result.append(line);
                        }
                            reader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Gson gson = new Gson();
                        Object data = gson.fromJson(result.toString(), Object.class);
                        Blockchain valid = new Blockchain();
                        String val = valid.isChainValid(data.getBlockchain(), data.getBalance());
                        if(!val.equals("OK")){
                            balanceAmountTextView.setText("" + val);
                            Toast.makeText(getApplicationContext(), "Dont have to be worry, it undergoes audit and your transactions are going to be safe :)..", Toast.LENGTH_SHORT).show();
                        }
                        else if(val.equals("OK")){
                            balanceAmountTextView.setText("Rs" + data.getBalance());
                            Toast.makeText(getApplicationContext(), "You Successfully.. fetched your account balance :)..", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Try again..(:", Toast.LENGTH_SHORT).show();
                        }
                    } else{}
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}