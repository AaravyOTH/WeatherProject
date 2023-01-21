package com.example.weatherproject;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity{
    EditText editText;
    Button ZipCodeSender;
    String ZipCode;
    JSONObject json;
    TextView textViewCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("JSON", "onCreate");


        editText = findViewById(R.id.ZipCodeInput);
        ZipCodeSender = findViewById(R.id.sendZipCode);
        textViewCity = findViewById(R.id.textViewCity);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ZipCode =  charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ZipCodeSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("zipcode", ZipCode);
                new DownloadWeatherData().execute("https://api.openweathermap.org/data/2.5/forecast?zip="+ ZipCode +"&appid=32d88b1ecd39ef961c7c86fe102d4406");

            }


    });
    }
    public EditText getEditText() {
        return editText;
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String jsonString = builder.toString(); //String
                json = new JSONObject(jsonString);

                Log.d("JSON", jsonString);

            }catch(IOException e){
                e.printStackTrace();
                Log.d("JSON", "IO EXCEPTION");
                Log.d("JSON", e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        /*    try {
                textViewCity.setText((CharSequence) json.get("city"));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }


}

//http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=32d88b1ecd39ef961c7c86fe102d4406
//api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid=524901&appid=32d88b1ecd39ef961c7c86fe102d4406
//api.openweathermap.org/data/2.5/forecast?zip=08824&appid=524901&appid=32d88b1ecd39ef961c7c86fe102d4406

//https://api.openweathermap.org/data/2.5/weather?zip=[zipcode],US&appid=32d88b1ecd39ef961c7c86fe102d4406
//THIS THE SHIT I NEED https://api.openweathermap.org/data/2.5/forecast?zip=08824&appid=32d88b1ecd39ef961c7c86fe102d4406