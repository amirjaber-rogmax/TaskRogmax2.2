package com.rogmax.amirjaber.taskrogmax22;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCreatePet = (Button) findViewById(R.id.create_pet);
        Button buttonCreatePerson = (Button) findViewById(R.id.create_person);
        Button buttonViewPets = (Button) findViewById(R.id.view_pets);
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

            }
        });
        buttonViewPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonViewPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
