package com.example.uffdcbank;

import static java.lang.Float.parseFloat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity{
    TextView scam; Button done;
        private RecyclerView recyclerView;
        private TransactionAdapter transactionAdapter;
        private List<Transaction> transactionList;
    int responseCode ;  StringBuilder result = new StringBuilder();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.history);
            scam = findViewById(R.id.scam);
            done = findViewById(R.id.done);
            Intent intentt = getIntent();
            String from = intentt.getStringExtra("username");
            recyclerView = findViewById(R.id.recycler_view_transactions);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            transactionList = new ArrayList<>();

            new Thread(() -> {
                try {
                    String apiUrl = "http://13.232.31.31:3335/history/"+from;
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
                                scam.setText("" + val);
                                Toast.makeText(getApplicationContext(), "Dont worry it undergoes audit and your transactions are going to be safe :)..", Toast.LENGTH_SHORT).show();
                            }
                            else if(val.equals("OK")){
                                for(int a=0; a<data.getBlockchain().size(); a++) {
                                    if(data.getBlockchain().get(a).getFrom()=="from" || data.getBlockchain().get(a).getFrom().equals(from)) {
                                        transactionList.add(new Transaction("" + data.getBlockchain().get(a).getTo(), "" + data.getBlockchain().get(a).getTimeStamp(), data.getBlockchain().get(a).getData(), "Debit"));
                                    }else{
                                        transactionList.add(new Transaction("" + data.getBlockchain().get(a).getFrom(), "" + data.getBlockchain().get(a).getTimeStamp(), data.getBlockchain().get(a).getData(), "Credit"));
                                        // transactionList.add(new Transaction("Refund from Store", "June 18, 2024", 50.00, "Completed"));
                                    }
                                    // Add more transactions as needed
                                }
                                transactionAdapter = new TransactionAdapter(transactionList);
                                recyclerView.setAdapter(transactionAdapter);
                                Toast.makeText(getApplicationContext(), "You Successfully.. fetched transaction history :)..", Toast.LENGTH_SHORT).show();
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
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }










