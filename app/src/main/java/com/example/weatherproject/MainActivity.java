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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.jar.JarEntry;


public class MainActivity extends AppCompatActivity{
    EditText editText;
    Button ZipCodeSender;
    String ZipCode;
    JSONObject json;
    TextView textViewCity;
    TextView Date;
    TextView currentHigh;
    TextView currentLow;
    TextView secondHigh;
    TextView secondLow;
    TextView thirdHigh;
    TextView thirdLow;
    TextView fourthHigh;
    TextView fourthLow;
    TextView fifthHigh;
    TextView fifthLow;
    TextView secondDate;
    TextView thirdDate;
    TextView fourthDate;
    TextView fifthDate;
    TextView qoute;
    ImageView currentImage;
    ImageView secondImage;
    ImageView thirdImage;
    ImageView fourthImage;
    ImageView fifthImage;


    String sunny = "Delphox used Sunny Day!";
    String rainy = "Lapras used Rain Dance!";
    String stormy = "A wild Thundurus Appeared!";
    String cloudy = "Ho-Oh is nowhere to be found!";
    String snow = "Articuno used Blizzard!";
    String clear = "Its a beautiful day in the Kanto Region!";
    String windy = "Pidgeotto Used Gust!";
    String sunnyTwo = "Its a fantastic day for Pokemon Training!";
    String cloudyTwo = "It feels like Lavendar Town! The clouds feel ominous";
    String snowTwo = "It feels like SnowPoint City";

    Date currentTime = Calendar.getInstance().getTime();
    int currentTime2 = Calendar.getInstance().getTime().getHours();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //Get The Current Hour along with tests

        Log.d("JSON", "onCreate");
        String currentHour = Integer.toString(currentTime2);
        Log.d("time", String.valueOf(currentTime));
        int closestHour = getClosestHour(currentTime2);
        String closestHourTest = Integer.toString(closestHour);
        Log.d("closestHour",closestHourTest);

        //
        qoute = findViewById(R.id.textViewQoute);
        currentImage = findViewById(R.id.imageViewCurrent);
        secondImage = findViewById(R.id.imageViewSecond);
        thirdImage = findViewById(R.id.imageViewThird);
        fourthImage = findViewById(R.id.imageViewFourth);
        fifthImage = findViewById(R.id.imageViewFifth);
        currentHigh = findViewById(R.id.textViewCurrentHigh);
        currentLow = findViewById(R.id.textViewCurrentLow);
        secondHigh = findViewById(R.id.textViewSecondHigh);
        secondLow = findViewById(R.id.textViewSecondLow);
        thirdHigh = findViewById(R.id.textViewThirdHigh);
        thirdLow = findViewById(R.id.textViewThirdLow);
        fourthHigh = findViewById(R.id.textViewFourthHigh);
        fourthLow = findViewById(R.id.textViewFourthLow);
        fifthHigh = findViewById(R.id.textViewFifthHigh);
        fifthLow = findViewById(R.id.textViewFifthLow);
        Date = findViewById(R.id.textViewCurrentDate);
        secondDate = findViewById(R.id.textViewSecondDate);
        thirdDate = findViewById(R.id.textViewThirdDate);
        fourthDate = findViewById(R.id.textViewFourthDate);
        fifthDate = findViewById(R.id.textViewFifthDate);
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
                try {
                    new DownloadWeatherData().execute("https://api.openweathermap.org/data/2.5/forecast?zip="+ ZipCode +"&appid=32d88b1ecd39ef961c7c86fe102d4406");

                }catch (Exception e){
                }

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
            try {
                Log.d("DATETESTFIRST", json.getJSONArray("list").getJSONObject(0).getString("dt_txt"));
                String str = json.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(11,13);
                String strDay = json.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(5,10);

                Date.setText(strDay + " " + getTime(str));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                textViewCity.setText((CharSequence) json.getJSONObject("city").getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String str = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp_max");
                String secondStr = KelvinToFahrenheit(str);
                currentHigh.setText("H: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String str = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp_min");
                String secondStr = KelvinToFahrenheit(str);
                currentLow.setText("L: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String str = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getString("temp_max");
                String secondStr = KelvinToFahrenheit(str);
                secondHigh.setText("H: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String str = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getString("temp_min");
                String secondStr = KelvinToFahrenheit(str);
                secondLow.setText("L: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String str = json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getString("temp_max");
                String secondStr = KelvinToFahrenheit(str);
                thirdHigh.setText("H: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }





            try {
                String str = json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getString("temp_min");
                String secondStr = KelvinToFahrenheit(str);
                thirdLow.setText("L: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String str = json.getJSONArray("list").getJSONObject(3).getJSONObject("main").getString("temp_max");
                String secondStr = KelvinToFahrenheit(str);
                fourthHigh.setText("H: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String str = json.getJSONArray("list").getJSONObject(3).getJSONObject("main").getString("temp_min");
                String secondStr = KelvinToFahrenheit(str);
                fourthLow.setText("L: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String str = json.getJSONArray("list").getJSONObject(4).getJSONObject("main").getString("temp_max");
                String secondStr = KelvinToFahrenheit(str);
                fifthHigh.setText("H: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String str = json.getJSONArray("list").getJSONObject(4).getJSONObject("main").getString("temp_min");
                String secondStr = KelvinToFahrenheit(str);
                fifthLow.setText("L: " + secondStr + "°f");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                Log.d("DATETESTSECOND", json.getJSONArray("list").getJSONObject(1).getString("dt_txt"));
                String str = json.getJSONArray("list").getJSONObject(1).getString("dt_txt").substring(11,13);
                String strDay = json.getJSONArray("list").getJSONObject(1).getString("dt_txt").substring(5,10);

                secondDate.setText(strDay + " " + getTime(str));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Log.d("DATETESTTHIRD", json.getJSONArray("list").getJSONObject(2).getString("dt_txt"));
                String str = json.getJSONArray("list").getJSONObject(2).getString("dt_txt").substring(11,13);
                String strDay = json.getJSONArray("list").getJSONObject(2).getString("dt_txt").substring(5,10);

                thirdDate.setText(strDay + " " + getTime(str));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Log.d("DATETESTFOURTH", json.getJSONArray("list").getJSONObject(3).getString("dt_txt"));
                String str = json.getJSONArray("list").getJSONObject(3).getString("dt_txt").substring(11,13);
                String strDay = json.getJSONArray("list").getJSONObject(3).getString("dt_txt").substring(5,10);
                fourthDate.setText(strDay + " " + getTime(str));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Log.d("DATETESTFIFTH", json.getJSONArray("list").getJSONObject(4).getString("dt_txt"));
                String str = json.getJSONArray("list").getJSONObject(4).getString("dt_txt").substring(11,13);
                String strDay = json.getJSONArray("list").getJSONObject(4).getString("dt_txt").substring(5,10);

                fifthDate.setText(strDay + " " + getTime(str));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String str = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id");
                currentImage.setImageResource(weatherPicture(str));
                String str2 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id");
                secondImage.setImageResource(weatherPicture(str2));
                String str3 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id");
                thirdImage.setImageResource(weatherPicture(str3));
                String str4 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id");
                fourthImage.setImageResource(weatherPicture(str4));
                String str5 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id");
                fifthImage.setImageResource(weatherPicture(str5));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                int i = weatherPicture(json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("id"));
                if(i == R.drawable.cloudy || i == R.drawable.partlycloudy || i == R.drawable.morecloudy || i == R.drawable.mostlycloudy){
                    int ran = (int) ( Math.random() * 2 + 1);
                    if(ran == 1){
                        qoute.setText(cloudy);
                    }
                    if(ran == 2){
                        qoute.setText(cloudyTwo);
                    }
                }
                if(i == R.drawable.hail || i == R.drawable.snowy || i == R.drawable.rainysnowy){
                    int ran = (int) ( Math.random() * 2 + 1);
                    if(ran == 1){
                        qoute.setText(snow);
                    }
                    if(ran == 2){
                        qoute.setText(snowTwo);
                    }
                }
                if(i == R.drawable.sunny){
                    int ran = (int) ( Math.random() * 3 + 1);
                    if(ran == 1){
                        qoute.setText(sunny);
                    }
                    if(ran == 2){
                        qoute.setText(sunnyTwo);
                    }
                    if(ran == 2){
                        qoute.setText(clear);
                    }
                }
                if(i == R.drawable.rainy || i == R.drawable.morerainy || i == R.drawable.partlyrainy){
                    qoute.setText(rainy);


                }
                if(i == R.drawable.thunderrain || i == R.drawable.cloudythunderrain){
                    qoute.setText(stormy);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

    public String KelvinToFahrenheit(String str){
        Double number = ((Double.valueOf(str) - 273.15) * 1.8) + 32;
        return String.format("%.2f", number);
   }
   public int weatherPicture(String str){
        int num = Integer.parseInt(str);
       switch (num) {
           case 804 : return R.drawable.cloudy;
           case 803: return R.drawable.morecloudy;
           case 802: return R.drawable.mostlycloudy;
           case 801: return R.drawable.partlycloudy;
           case 800: return R.drawable.sunny;

           case 600:
           case 620:
           case 602:
           case 601:
               return R.drawable.snowy;
           case 611:
           case 613:
           case 612:
               return R.drawable.hail;
           case 615:
           case 616:
               return R.drawable.rainysnowy;
           case 502:
           case 531:
           case 522:
           case 521:
           case 520:
           case 511:
           case 504:
           case 503:
               return R.drawable.rainy;
           case 500:
           case 321:
           case 501:
           case 312:
           case 313:
           case 314:
               return R.drawable.morerainy;
           case 300:
           case 301:
           case 302:
           case 310:
           case 311:
               return R.drawable.partlyrainy;
           case 232:
           case 221:
           case 210:
           case 212:
               return R.drawable.cloudythunderrain;
           case 200:
           case 201:
           case 230:
           case 231:
                return R.drawable.thunderrain;
           default: return R.drawable.cloudy;
       }

   }

    public String getTime(String str){
        int num = Integer.parseInt(str);
        if(num > 12){
            num = num - 17;
            if(num < 0){
                String string =   num + 12 + " am";
                return string;
            }
            else {
                String string = num + " pm";
                return string;
            }
        }else if(num == 0) {
            return "7 pm";
        }else{
            num = num - 5;
            if(num < 0) {
                String string = 12 + num + " pm";
                return string;
            }
            else{
                String string = num + " am";
                return string;
            }
        }
    }

    public int getClosestHour(int Time){
           if(Time % 3 == 0)
            return Time;
        if(Time % 3 == 1)
            return Time - 1;
        if (Time % 3 == 2)
            return Time + 1;
        else{
            return 0;
        }
    }
}

//http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=32d88b1ecd39ef961c7c86fe102d4406
//api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid=524901&appid=32d88b1ecd39ef961c7c86fe102d4406
//api.openweathermap.org/data/2.5/forecast?zip=08824&appid=524901&appid=32d88b1ecd39ef961c7c86fe102d4406

//https://api.openweathermap.org/data/2.5/weather?zip=[zipcode],US&appid=32d88b1ecd39ef961c7c86fe102d4406
//https://api.openweathermap.org/data/2.5/forecast?zip=08824&appid=32d88b1ecd39ef961c7c86fe102d4406
//WAS DOING KELVIN TO FARANIGHT
//add another color
//change font
//think about wind and the other shit