package com.rogmax.amirjaber.taskrogmax22;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rogmax.amirjaber.taskrogmax22.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class CreatePetActivity extends AppCompatActivity {

    EditText petName;
    Spinner spinnerType, spinnerSub;
    List<Pet> pets = new ArrayList<Pet>();
    ListView petListView;
    ArrayAdapter<Pet> petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        petListView = (ListView) findViewById(R.id.listView);
        petName = (EditText) findViewById(R.id.et_pet_name);
        spinnerType = (Spinner) findViewById(R.id.type);
        spinnerSub = (Spinner) findViewById(R.id.sub_type);

        final Button btnAddPet = (Button) findViewById(R.id.btn_add_pet);
        btnAddPet.setEnabled(false);
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPet(String.valueOf(spinnerType.getSelectedItem()), String.valueOf(spinnerSub.getSelectedItem()), String.valueOf(petName.getText()));
                populatePetList();
                toastMessage(petName.getText().toString() + " has been created successfully");

            }
        });


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

        // Spinner adapters
        spinnerSub.setEnabled(false);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.type_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);

                if ("".equals(type)) {
                    spinnerSub.setEnabled(false);
                    btnAddPet.setEnabled(false);
                    petName.setEnabled(false);
                }else {
                    spinnerSub.setEnabled(true);
                    btnAddPet.setEnabled(true);
                    petName.setEnabled(true);
                }

                if ("Cat".equals(type)) {
                    populateCat();
                }
                if ("Dog".matches(type)) {
                    populateDog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void populatePetList() {
        petAdapter = new PetListAdapter();
        petListView.setAdapter(petAdapter);
    }

    private void addPet(String type, String subtype, String name) {
        pets.add(new Pet(0, type, subtype, name));
    }

    private class PetListAdapter extends ArrayAdapter<Pet> {

        private PetListAdapter() {
            super(CreatePetActivity.this, R.layout.listview_pet, pets);
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_pet, parent, false);

            Pet currentPet = pets.get(position);

            TextView name = view.findViewById(R.id.petName);
            name.setText(currentPet.getName());
            TextView type = view.findViewById(R.id.petType);
            type.setText(currentPet.getType());
            TextView subType = view.findViewById(R.id.petSubType);
            subType.setText(currentPet.getSubType());

            return view;

        }
    }


    // Populate second spinner based on first one
    void populateCat() {
        String[] cat = {"black", "orange", "white"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cat);
        spinnerSub.setAdapter(adapter);
    }

    void populateDog() {
        String[] dog = {"poodle", "husky", "pit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dog);
        spinnerSub.setAdapter(adapter);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
