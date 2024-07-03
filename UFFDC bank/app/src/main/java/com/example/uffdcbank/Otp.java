package com.example.uffdcbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Otp extends AppCompatActivity {
    private EditText otp1, otp2, otp3, otp4; Button contin;
    private TextView resendbtn; String mobi = "9618162269"; private boolean resendEnabled = false;
    private int resendTime = 60, selected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Button cont = findViewById(R.id.button2);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        contin = findViewById(R.id.button2);
        resendbtn = findViewById(R.id.resend);
        final TextView mobile = findViewById(R.id.mobilee);
        final String getMobile = getIntent().getStringExtra(mobi);
        mobile.setText(getMobile);

        otp4.addTextChangedListener(textWatch);
        otp3.addTextChangedListener(textWatch);
        otp2.addTextChangedListener(textWatch);
        otp1.addTextChangedListener(textWatch);
        showkeyboard(otp4);

        startCountDownTimer();
        resendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled){
                    startCountDownTimer();
                }
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String generateotp = otp4.getText().toString()+otp3.getText().toString()+otp2.getText().toString()+otp1.getText().toString();
                if(generateotp.length()==4){

                }
            }
        });
        contin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Successfully registered!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showkeyboard(EditText otp){
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }
    private void startCountDownTimer(){
        resendEnabled = false;
        resendbtn.setTextColor(Color.parseColor("#99000000"));
        new CountDownTimer(resendTime*60,1000){
            @Override
            public void onTick(long millisUntilFinished){
                resendbtn.setText("Will Resend code within("+(millisUntilFinished/60)+")");
            }
            @Override
            public void onFinish(){
                resendEnabled = true;
                resendbtn.setText("Resend");
                resendbtn.setTextColor(getResources().getColor(R.color.purple_700));
            }
        }.start();
    }
    private final TextWatcher textWatch = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after){

        }
        @Override
        public void afterTextChanged(Editable s){
            if(s.length() > 0){
                if(selected == 0){
                    selected =1;
                    otp3.requestFocus();
                }
                else if(selected == 1){
                    selected =2;
                    showkeyboard(otp2);
                }else if(selected == 2){
                    selected =3;
                    showkeyboard(otp1);
                }
            }
        }
    };
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (selected == 3) {
                selected = 2;
                showkeyboard(otp2);
            } else if (selected == 2) {
                selected = 1;
                showkeyboard(otp3);
            } else if (selected == 1) {
                selected = 0;
                showkeyboard(otp4);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode,event);
        }

    }
}
