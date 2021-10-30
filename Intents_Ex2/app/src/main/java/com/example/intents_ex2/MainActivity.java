package com.example.intents_ex2;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Button searchBtn, dialBtn, optionsBtn;
    private EditText nameFromUser, webAddress, phoneFromUser, webFromUser;
    private String nameStr;
    private ImageView mic;
    public static String number;
    static final String INPUT_DATA_KEY = "MainActivity.INPUT_DATA_KEY";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);

        dialBtn = findViewById(R.id.dialBtn);
        dialBtn.setOnClickListener(this);

        optionsBtn = findViewById(R.id.opsBtn);
        optionsBtn.setOnClickListener(this);

        webAddress = findViewById(R.id.addressEditView);

        nameFromUser = findViewById(R.id.nameEditText);

        phoneFromUser = findViewById(R.id.phoneNumEditView);

    }

    public void onClick(View view) {
        nameStr = nameFromUser.getText().toString();

        if (searchBtn == view) {
            if (webAddress.getText().toString().equals("")) {
                Toast popUp = Toast.makeText(this, "Please enter a website address", Toast.LENGTH_SHORT);
                popUp.show();
            }
            else
                SearchByAddress();
        }

        if (dialBtn == view) {
            number = phoneFromUser.getText().toString();

            if (nameStr.equals("")) {
                Toast popUp = Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT);
                popUp.show();
            }
            else {
                if (phoneFromUser.getText().toString().equals("")) {
                    Toast popUp = Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT);
                    popUp.show();
                }
                else {
                    if (validNumber(number)) {
                        Intent intent = new Intent(this, SecondActivity.class);
                        //String nameFromUser = inputFromUser.getText().toString();
                        intent.putExtra(INPUT_DATA_KEY, nameStr + ", Are you sure you want to dial to " + number + "?");
                        startActivity(intent);
                    } else
                        ;
                }
            }
        }

        if(optionsBtn == view) {
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        }
    }

    public boolean validNumber(String str){
        if (str.equals("")) {
            Toast popUp = Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_LONG);
            popUp.show();
            return false;
        }
        else if (str.length() > 1 && str.length() != 9 && str.length() != 10) {
            Toast popUp = Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_LONG);
            popUp.show();
            return false;
        }
        else {
            return true;
        }
    }

    public void SpeechRecognizer(View view){
        mic = findViewById(R.id.micBtn);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        }
        else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    public void SearchByAddress(){
        webFromUser = findViewById(R.id.addressEditView);
        String addressStr = webFromUser.getText().toString();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, addressStr);
        startActivity(intent);
    }


}
