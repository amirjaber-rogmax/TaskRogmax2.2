package com.rogmax.amirjaber.taskrogmax22;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rogmax.amirjaber.taskrogmax22.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class CreatePetActivity extends AppCompatActivity {

    EditText petName;
    Spinner spinnerType, spinnerSub;
    List<Pet> Pets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        petName = (EditText) findViewById(R.id.et_pet_name);
        spinnerType = (Spinner) findViewById(R.id.type);
        spinnerSub = (Spinner) findViewById(R.id.sub_type);
        final Button btnAddPet = (Button) findViewById(R.id.btn_add_pet);

        btnAddPet.setEnabled(false);

        petName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnAddPet.setEnabled(!petName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("Pet has been created successfully");
            }
        });


        // Spinner adapters
        spinnerSub.setEnabled(false);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.type_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);

                if ("".equals(type)){
                    spinnerSub.setEnabled(false);
                }

                if ("Cat".equals(type)) {
                    spinnerSub.setEnabled(true);
                    populateCat();
                }
                if ("Dog".matches(type)) {
                    spinnerSub.setEnabled(true);
                    populateDog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class PetListAdapter extends ArrayAdapter<Pet>{

        public PetListAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }
    }

    // Populate second spinner based on first one
    void populateCat() {
        String[] cat = {"black","orange","white"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cat);
        spinnerSub.setAdapter(adapter);
    }
    void populateDog() {
        String[] dog = {"poodle","husky","pit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dog);
        spinnerSub.setAdapter(adapter);
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
