package com.example.uffdcbank;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Useridcreation extends AppCompatActivity {
    TextInputEditText inp1,inp2,inp3,inp5;
    Button reg;
    String userid, password, confirmpassword, ph; int responseCode, responseCodee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useridcreation);
        inp1 = findViewById(R.id.textInputEditText);
        inp2 = findViewById(R.id.textInputEditText1);
        inp3 = findViewById(R.id.textInputEditText2);
        inp5 = findViewById(R.id.textInputEditText5);
        reg = findViewById(R.id.button5);
        reg.setOnClickListener(view -> {
            userid = String.valueOf(inp1.getText());
            ph = String.valueOf(inp5.getText());
            password = String.valueOf(inp2.getText());
            confirmpassword = String.valueOf(inp3.getText());
            if(Objects.equals(password, confirmpassword)){
                new Thread(() -> {
                    try {
                        String apiUrl = "http://10.0.2.2:3333/useridcreation/" + userid + "/" + password + "/" + ph;
                        URL url = new URL(apiUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();

                        runOnUiThread(() -> {
                            if (responseCode == 201) {
                                Blockchain genesis = new Blockchain();
                                genesis.main(userid);
                                String apiUrll = "http://13.232.31.31:3333/useridcreationn/" + userid + "/" + genesis.getGenesis();

                                new Thread(() -> {
                                    try {
                                        URL urll = new URL(apiUrll);
                                        HttpURLConnection connectionn = (HttpURLConnection) urll.openConnection();
                                        connectionn.setRequestMethod("GET");
                                        int responseCodee = connectionn.getResponseCode();

                                        runOnUiThread(() -> {
                                            if (responseCodee == 201) {
                                                Toast.makeText(getApplicationContext(), "You are successfully got registered..(: Happy mobile banking..", Toast.LENGTH_SHORT).show();
                                                Intent intents = new Intent(Useridcreation.this, Login.class);
                                                startActivity(intents);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Registration failed..", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (IOException e) {
                                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error during registration process.", Toast.LENGTH_SHORT).show());
                                    }
                                }).start();
                            } else if (responseCode == 501) {
                                Toast.makeText(getApplicationContext(), "User ID already taken.. try another..(:", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Password and confirm password must be the same..(:", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error during registration process.", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }

        });

    }
}


