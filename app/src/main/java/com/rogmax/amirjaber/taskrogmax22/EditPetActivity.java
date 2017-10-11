package com.rogmax.amirjaber.taskrogmax22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rogmax.amirjaber.taskrogmax22.helpers.DatabaseHelper;
import com.rogmax.amirjaber.taskrogmax22.models.Pet;

public class EditPetActivity extends AppCompatActivity {

    EditText petName;
    Spinner spinnerType, spinnerSub;
    DatabaseHelper dbHelper;
    CreatePetActivity petObj;

    private final String[] catColors = {"black", "orange", "white"};
    private final String[] dogColors = {"poodle", "husky", "pit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        petObj = new CreatePetActivity();

        petName = (EditText) findViewById(R.id.et_pet_name);
        spinnerType = (Spinner) findViewById(R.id.type);
        spinnerSub = (Spinner) findViewById(R.id.sub_type);
        dbHelper = new DatabaseHelper(this);

        final Pet pet = CreatePetActivity.getPets().get(CreatePetActivity.clickedItemIndex);

        // Spinner adapters
        petName.setText(pet.getName());

        final ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_spinner, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        spinnerType.setSelection(typeAdapter.getPosition(pet.getType()));

        final ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chooseColors("Cat"));
        final ArrayAdapter<String> dogAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chooseColors("Dog"));
        spinnerSub.setAdapter(catAdapter);
        spinnerSub.setSelection(catAdapter.getPosition(pet.getSubType()));

        // Set a listener to first spinner and populate second spinner
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);
                if ("".equals(type)) {
                    spinnerSub.setEnabled(false);
                } else if ("Cat".equals(type)) {
                    spinnerSub.setEnabled(true);
                    spinnerSub.setAdapter(catAdapter);
                    spinnerSub.setSelection(catAdapter.getPosition(pet.getSubType()));
                } else if ("Dog".equals(type)) {
                    spinnerSub.setEnabled(true);
                    spinnerSub.setAdapter(dogAdapter);
                    spinnerSub.setSelection(dogAdapter.getPosition(pet.getSubType()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button btnEdit = (Button) findViewById(R.id.btn_edit_pet);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pet.set_name(String.valueOf(petName.getText()));
                pet.set_type(String.valueOf(spinnerType.getSelectedItem()));
                pet.set_subtype(String.valueOf(spinnerSub.getSelectedItem()));

                dbHelper.updatePet(pet);

                Intent returnIntent = new Intent(EditPetActivity.this, CreatePetActivity.class);
                startActivity(returnIntent);
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(EditPetActivity.this, CreatePetActivity.class);
                startActivity(cancel);
            }
        });

    }

    private String[] chooseColors(String type) {
        if (type.equals("Cat")) {
            return catColors;
        }
        return dogColors;
    }

}
