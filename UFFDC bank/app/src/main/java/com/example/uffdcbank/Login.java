package com.example.uffdcbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextInputEditText userId, password;
    Button login, register, exit;
    String user, pass; int responseCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId = findViewById(R.id.userid);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button8);
        register = findViewById(R.id.button9);
        exit = findViewById(R.id.button10);

        exit.setOnClickListener(view -> System.exit(0));

        register.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Register here for mobile banking..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            user = String.valueOf(userId.getText());
            pass = String.valueOf(password.getText());
            new Thread(() -> {
            try {
                String apiUrl = "http://13.232.31.31:3333/login/"+user+"/"+pass+"";
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                responseCode = connection.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode==201) {
                        Toast.makeText(getApplicationContext(), "You Successfully.. logged in..", Toast.LENGTH_SHORT).show();
                        Intent intents = new Intent(Login.this, Home.class);
                        intents.putExtra("username", user);
                        startActivity(intents);
                    }else{Toast.makeText(getApplicationContext(), "Try again..(:", Toast.LENGTH_SHORT).show();}
                    });
            }
            catch (Exception e) {
                e.printStackTrace();
            }}).start();

           }); }}
