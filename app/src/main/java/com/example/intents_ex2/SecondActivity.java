package com.example.intents_ex2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    private Button yesBtn, noBtn;
    //private EditText webFromUser, phoneFromUser;
    private EditText hour, minutes, location;
    ImageButton alarmButton, googleSearchButton;
    FloatingActionButton backButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        backButton = findViewById(R.id.fabBackBtn);
        backButton.setOnClickListener(this);

        nextButton = findViewById(R.id.fabNextBtn);
        nextButton.setOnClickListener(this);

        yesBtn = findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(this);

        noBtn = findViewById(R.id.noBtn);
        noBtn.setOnClickListener(this);

        alarmButton = findViewById(R.id.alarmBtn);
        alarmButton.setOnClickListener(this);

        googleSearchButton = findViewById(R.id.googleSearchBtn);
        googleSearchButton.setOnClickListener(this);

        hour = findViewById(R.id.hourEditText);
        minutes = findViewById(R.id.minutesEditText);

        location = findViewById(R.id.locationEditText);


        Intent intentFromMain = getIntent(); // get the intent that initiated the activity (only 1 intent at a time)
        String nameFromFirst = intentFromMain.getStringExtra(MainActivity.INPUT_DATA_KEY);
        TextView textView = findViewById(R.id.messageText);
        textView.setText(nameFromFirst);

        googleSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationString = location.getText().toString();
                Uri locationUri = Uri.parse("geo:0,0?q="+locationString);
                Intent intent = new Intent(Intent.ACTION_VIEW,locationUri);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
                else{
                    // if there is no compatible app print to logcat
                    Log.d("geoLocationOpenError", "There is no app to open a google maps API location");
                }
            }
        });
    }

    public void onClick(View view){
        if (backButton == view){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        if (nextButton == view){
            Intent intent = new Intent(this,ThirdActivity.class);
            startActivity(intent);
        }

        if (yesBtn == view)
            DialByNumber();
        if (noBtn == view){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (alarmButton == view){
            if(hour != null && minutes != null) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hour.getText().toString()));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(minutes.getText().toString()));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(SecondActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void DialByNumber() {
        Uri call = Uri.parse("tel:" + MainActivity.number);
        startActivity(new Intent(Intent.ACTION_DIAL, call));
    }


}
