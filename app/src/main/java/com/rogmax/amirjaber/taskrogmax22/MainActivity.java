package com.rogmax.amirjaber.taskrogmax22;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rogmax.amirjaber.taskrogmax22.helpers.DatabaseHelper;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();



        Button buttonCreatePet = (Button) findViewById(R.id.create_pet);
        Button buttonCreatePerson = (Button) findViewById(R.id.create_person);
        Button buttonViewPeople = (Button) findViewById(R.id.view_people);

        buttonCreatePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePetActivity.class);
                startActivity(intent);
            }
        });

        buttonCreatePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePersonActivity.class);
                startActivity(intent);
            }
        });

        buttonViewPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewPeopleActivity.class);;
                startActivity(intent);
            }
        });

    }
}
