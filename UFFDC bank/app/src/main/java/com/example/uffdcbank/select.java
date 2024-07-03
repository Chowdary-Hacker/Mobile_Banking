package com.example.uffdcbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class select extends AppCompatActivity {

    private String[] userIds ;// = {"User77", "User18", "User79", "User68", "User24"};
    int responseCode; StringBuilder result = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Intent intentt = getIntent();
        String user = intentt.getStringExtra("username");
        new Thread(() -> {
            try {
                String apiUrl = "http://13.232.31.31:3333/getUsers";
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                responseCode = connection.getResponseCode();

                BufferedReader reader = null;
                if (responseCode == 201) {
                    try {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {
                        Gson gson = new Gson();
                        userIds = gson.fromJson(result.toString(), String[].class);

                        ListView listView = findViewById(R.id.userListView);
                        Button proceedButton = findViewById(R.id.proceedButton);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_single_choice, userIds);
                        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Handle item click if needed
                            }
                        });

                        proceedButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int selectedItemPosition = listView.getCheckedItemPosition();
                                if (selectedItemPosition != ListView.INVALID_POSITION) {
                                    String selectedUserId = userIds[selectedItemPosition];
                                    // Proceed with the selected user ID
                                    Toast.makeText(select.this, "Enter amount for ur Selected User ID: " + selectedUserId,
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Transfer.class);
                                    intent.putExtra("username", user);
                                    intent.putExtra("to", selectedUserId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(select.this, "Please select a user ID",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Try again..(:", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}

