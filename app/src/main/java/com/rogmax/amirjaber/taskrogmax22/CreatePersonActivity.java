package com.rogmax.amirjaber.taskrogmax22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rogmax.amirjaber.taskrogmax22.helpers.DatabaseHelper;
import com.rogmax.amirjaber.taskrogmax22.models.Person;
import com.rogmax.amirjaber.taskrogmax22.models.Pet;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class CreatePersonActivity extends AppCompatActivity {

    private static List<Person> persons = new ArrayList<>();
    public static List<Person> getPersons() {
        return persons;
    }


    EditText personName, personAge;
    Spinner spinnerPet;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);
        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        // Initialise all items
        personName = (EditText) findViewById(R.id.et_name);
        personAge = (EditText) findViewById(R.id.et_age);
        spinnerPet = (Spinner) findViewById(R.id.s_pet);
        dbHelper = new DatabaseHelper(this);


        // Create, initialise button and disable it
        final Button btnAddPerson = (Button) findViewById(R.id.b_addPerson);
        btnAddPerson.setEnabled(false);

        // Add text change listener to pet name field
        personName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnAddPerson.setEnabled(String.valueOf(personName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pet selectedPet = dbHelper.findPetByName(spinnerPet.getSelectedItem().toString());
                Person person = new Person(dbHelper.getPeopleCount(), String.valueOf(personName.getText()),
                        String.valueOf(personAge.getText()), String.valueOf(spinnerPet.getSelectedItem()), selectedPet.getId());
                if (!personExists(person)) {
                    dbHelper.createPerson(person);
                    getPersons().add(person);
                    toastMessage(String.valueOf(personName.getText().toString()) + " has been created successfully");

                    return;
                }
                toastMessage(String.valueOf(personName.getText()) + " already exists. Please use a different name");

            }
        });

        // Spinner data
        loadSpinnerData();

    }

    // Load spinner array from database
    public void loadSpinnerData() {

        ArrayAdapter spinnerAdapter;

        ArrayList<Pet> pets = (ArrayList<Pet>) dbHelper.getAllPets();
        spinnerAdapter = new ArrayAdapter<>(CreatePersonActivity.this,
                android.R.layout.simple_spinner_dropdown_item, pets);
        spinnerPet.setAdapter(spinnerAdapter);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    // Check if the person already exists
    private boolean personExists(Person person) {
        for (Person p : getPersons()) {
            if (person.getName().equalsIgnoreCase(p.getName())) {
                return true;
            }
        }
        return false;
    }

    // Custom toast message
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
