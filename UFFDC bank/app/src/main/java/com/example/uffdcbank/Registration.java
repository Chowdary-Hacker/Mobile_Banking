package com.example.uffdcbank;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.net.HttpURLConnection;
import java.net.URL;
public class Registration extends AppCompatActivity {
    TextInputEditText inp1, inp2, inp3, inp4, inp5;
    Button sub, Exit, login;
    ImageButton img;
    String Acc, ifsc, pan, dob, ph;
    TextView textviewerror;
    ProgressBar progress;
    int responseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        inp1 = findViewById(R.id.textInputEditText);
        inp2 = findViewById(R.id.textInputEditText2);
        inp3 = findViewById(R.id.textInputEditText3);
        inp4 = findViewById(R.id.textInputEditText4);
        inp5 = findViewById(R.id.textInputEditText5);
        sub = findViewById(R.id.button);
        Exit = findViewById(R.id.button4);
        login = findViewById(R.id.button3);
        img = findViewById(R.id.imageButton2);
        textviewerror = findViewById(R.id.error);
        progress = findViewById(R.id.loading);

        sub.setOnClickListener(view -> {
            textviewerror.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            Acc = String.valueOf(inp1.getText());
            ifsc = String.valueOf(inp2.getText());
            pan = String.valueOf(inp3.getText());
            dob = String.valueOf(inp4.getText());
            ph = String.valueOf(inp5.getText());
            new Thread(() -> {
                try {
                    String apiUrl = "http://13.232.31.31:3333/register/" + Acc + "/" + ifsc + "/" + pan + "/" + dob + "/"+ph;
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();
                    runOnUiThread(() -> {
                        if (responseCode == 201) {
                            Toast.makeText(getApplicationContext(), "Registration Successful !! Now choose userid ..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Useridcreation.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "\uD83D\uDE14 Try agin..", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}