package com.example.intents_ex2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class ThirdActivity extends AppCompatActivity {

    ImageView imageView;
    FloatingActionButton back;
    Button addContact, calendar;
    EditText phone, name, email, titleEvent, locationEvent, descriptionEvent, hourEvent, minutesEvent, dayEvent, monthEvent, yearEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        addContact = findViewById(R.id.addContactBtn);
        phone = findViewById(R.id.contactPhoneNumber);
        name = findViewById(R.id.contactName);
        email = findViewById(R.id.contactEmail);
        back = findViewById(R.id.fabBackBtn);

        /*titleEvent = findViewById(R.id.titleEvent);
        locationEvent = findViewById(R.id.locationEvent);
        descriptionEvent = findViewById(R.id.locationEvent);
        hourEvent = findViewById(R.id.hourEvent);
        minutesEvent = findViewById(R.id.minutesEvent);
        dayEvent = findViewById(R.id.dayEvent);
        monthEvent = findViewById(R.id.monthEvent);
        yearEvent = findViewById(R.id.yearEvent);*/

        imageView = findViewById(R.id.imageView1);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() &&
                        !phone.getText().toString().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText().toString());
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText().toString());

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ThirdActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ThirdActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!titleEvent.getText().toString().isEmpty() &&
                        !locationEvent.getText().toString().isEmpty()) {
                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setData(CalendarContract.Events.CONTENT_URI);
                    calIntent.putExtra(CalendarContract.Events.TITLE, titleEvent.getText().toString());
                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, locationEvent.getText().toString());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, descriptionEvent.getText().toString());
                    if(calIntent.resolveActivity(getPackageManager()) != null){
                        startActivity(calIntent);
                    }
                    else{
                        Toast.makeText(ThirdActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(ThirdActivity.this, "Please fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage(imageView);
            }
        });

    }

    public void openImage(View view) {
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            imageView.setImageURI(uri);
        }
    }
}
