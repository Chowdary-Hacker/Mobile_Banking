package com.example.uffdcbank;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Transfer extends AppCompatActivity{
    TextView balanceAmountTextView; Button pay; Button done; EditText amoun; String amount;  int responseCode; int responseCodee; StringBuilder result = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        pay = findViewById(R.id.pay);
        done = findViewById(R.id.done);
        amoun = findViewById(R.id.editTextAmount);
        balanceAmountTextView = findViewById(R.id.balanceAmountTextView);
        Intent intentt = getIntent();
        String from = intentt.getStringExtra("username");
        String to = intentt.getStringExtra("to");
        pay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                amount = String.valueOf(amoun.getText());
                new Thread(() -> {
                    try {
                        String apiUrl = "http://13.232.31.31:3333/transaction/"+amount+"/"+from+"/"+to;
                        URL url = new URL(apiUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();

                        runOnUiThread(() -> {
                            if (responseCode == 201) {
                                new Thread(() -> {
                                    BufferedReader reader = null;
                                    StringBuilder result = new StringBuilder();
                                    try {
                                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                        String line;
                                        while ((line = reader.readLine()) != null) {
                                            result.append(line);
                                        }
                                        reader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Gson gson = new Gson();
                                    Object data = gson.fromJson(result.toString(), Object.class); // Assuming UserResponse is your data model class
                                    Blockchain valid = new Blockchain();
                                    String val = valid.isChainValid(data.getBlockchain(), data.getBalance());
System.out.println("bal - "+data.getBalance());
                                    runOnUiThread(() -> {
                                        if (!val.equals("OK")) {
                                            balanceAmountTextView.setText(val);
                                            Toast.makeText(getApplicationContext(), "Don't worry it undergoes audit and your transactions are going to be safe :)", Toast.LENGTH_SHORT).show();
                                        } else if (val.equals("OK")) {
                                            new Thread(() -> {
                                                try {
                                                    String fromHistory = valid.addBlock(data.getBlockchain(), amount, from, to, "" + (parseFloat(data.getBalance()) - parseFloat(amount)), "personal"); System.out.println("transf = "+data.getBlockchainto().size()+" - "+gson.toJson(data.getBlockchainto()));
                                                    String toHistory = valid.addBlock(data.getBlockchainto(), amount, from, to, "" + (parseFloat(data.getBlockchainto().get(data.getBlockchainto().size() - 1).getBalance()) + parseFloat(amount)), "personal");

                                                    String apiUrll = "http://13.232.31.31:3333/transactionContinue/" + amount + "/" + from + "/" + to + "/" + fromHistory + "/" + toHistory;
                                                    URL urll = new URL(apiUrll);
                                                    HttpURLConnection connectionn = (HttpURLConnection) urll.openConnection();
                                                    connectionn.setRequestMethod("GET");
                                                    int responseCodee = connectionn.getResponseCode();

                                                    runOnUiThread(() -> {
                                                        if (responseCodee == 201) {
                                                            balanceAmountTextView.setText("TRANSACTION SUCCESSFUL");
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Try again :(", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }).start();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Try again :(", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }).start();
                            } else {
                                Toast.makeText(getApplicationContext(), "Try again :(", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }});
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
