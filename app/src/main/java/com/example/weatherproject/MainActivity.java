package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("JSON", "onCreate");
        DownloadWeatherData asycTask = new DownloadWeatherData();
        asycTask.execute("http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=32d88b1ecd39ef961c7c86fe102d4406");
    }
    private class DownloadWeatherData extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            Log.d("JSON", "doInBackground");

            try{
                Log.d("JSON", strings[0]);

                URL REST = new URL(strings[0]);
                URLConnection connect = REST.openConnection();
                Log.d("JSON", connect.toString());
                InputStream stream = connect.getInputStream();
                //BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuilder builder = new StringBuilder();
//                String line;
//                while((line = reader.readLine()) != null) {
//                    builder.append(line);
//                }
//                String jsonString = builder.toString();
//                JSONObject json = new JSONObject(jsonString);

                Log.d("JSON", "here!");

            }catch(IOException e){
                e.printStackTrace();
                Log.d("JSON", "IO EXCEPTION");

            }
            return null;
        }
    }
}


//http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=32d88b1ecd39ef961c7c86fe102d4406