package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}


//http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=32d88b1ecd39ef961c7c86fe102d4406