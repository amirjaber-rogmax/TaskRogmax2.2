package com.rogmax.amirjaber.taskrogmax22;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
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
import android.R.layout;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rogmax.amirjaber.taskrogmax22.helpers.DatabaseHelper;
import com.rogmax.amirjaber.taskrogmax22.models.Pet;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class CreatePetActivity extends AppCompatActivity {

    private static final String LOG = "CreatePet";
    private static final int EDIT = 0, DELETE = 1;
    private static List<Pet> pets = new ArrayList<>();
    static int clickedItemIndex;

    EditText petName;
    Spinner spinnerType, spinnerSub;
    ListView petListView;
    DatabaseHelper dbHelper;
    ArrayAdapter<Pet> petAdapter;

    public static List<Pet> getPets() {
        return pets;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        // Initialise all items
        petListView = (ListView) findViewById(R.id.listView);
        petName = (EditText) findViewById(R.id.et_pet_name);
        spinnerType = (Spinner) findViewById(R.id.type);
        spinnerSub = (Spinner) findViewById(R.id.sub_type);
        dbHelper = new DatabaseHelper(getApplicationContext());


        // Create, initialise button and disable it
        final Button btnAddPet = (Button) findViewById(R.id.btn_add_pet);
        btnAddPet.setEnabled(false);

        // Set a listener to add pet
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pet pet = new Pet(dbHelper.getPetsCount(), String.valueOf(spinnerType.getSelectedItem()), String.valueOf(spinnerSub.getSelectedItem()), String.valueOf(petName.getText()));
                if (!petExists(pet)) {
                    dbHelper.createPet(pet);
                    getPets().add(pet);
                    petAdapter.notifyDataSetChanged();
                    toastMessage(String.valueOf(petName.getText().toString()) + " has been created successfully");

                    return;
                }
                toastMessage(String.valueOf(petName.getText()) + " already exists. Please use a different name");

            }
        });

        // Set a listener to each view in the list
        registerForContextMenu(petListView);
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedItemIndex = position;
                Log.d(LOG, "onItemClick: You have clicked on item number " + clickedItemIndex);
            }
        });


        // Add text change listener to pet name field
        petName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnAddPet.setEnabled(String.valueOf(petName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Spinner adapters
        spinnerSub.setEnabled(false);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.type_spinner, layout.simple_spinner_item);
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Set a listener to first spinner and populate second spinner
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);

                if ("".equals(type)) {
                    spinnerSub.setEnabled(false);
                    btnAddPet.setEnabled(false);
                    petName.setEnabled(false);
                } else {
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

        // Populate list if pets in database are not 0
        if (dbHelper.getPetsCount() != 0){
            getPets().clear();
            getPets().addAll(dbHelper.getAllPets());
        }
        populatePetList();
    }


    // Create adapter for pet list
    private class PetListAdapter extends ArrayAdapter<Pet> {

        private PetListAdapter() {
            super(CreatePetActivity.this, R.layout.listview_pet, getPets());
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_pet, parent, false);

            Pet currentPet = getPets().get(position);

            TextView name = view.findViewById(R.id.petName);
            name.setText(currentPet.getName());
            TextView type = view.findViewById(R.id.petType);
            type.setText(currentPet.getType());
            TextView subType = view.findViewById(R.id.petSubType);
            subType.setText(currentPet.getSubType());

            return view;

        }
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case EDIT:
                Intent editIntent = new Intent(getApplicationContext(), EditPetActivity.class);
                startActivity(editIntent);
                break;
            case DELETE:
                dbHelper.deletePet(getPets().get(clickedItemIndex));
                getPets().remove(clickedItemIndex);
                petAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    // Check if pet already exists in the list
    private boolean petExists(Pet pet) {
        String name = pet.getName();
        int petCount = getPets().size();

        for (int i = 0; i < petCount; i++) {
            if (name.compareToIgnoreCase(getPets().get(i).getName()) == 0)
                return true;
        }

        return false;
    }

    // Add context menu items
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_edit);
        menu.setHeaderTitle("Pet Edit");
        menu.add(Menu.NONE, EDIT, Menu.NONE, "Edit Pet");
        menu.add(Menu.NONE, DELETE, Menu.NONE, "Delete Pet");
    }

    // Populate list view
    private void populatePetList() {
        Log.d(LOG, "CreatePet: Displaying data in ListView");
        petAdapter = new PetListAdapter();
        petListView.setAdapter(petAdapter);
    }

    // Populate second spinner based on first one
    private void populateCat() {
        String[] cat = {"black", "orange", "white"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cat);
        spinnerSub.setAdapter(adapter);
    }

    private void populateDog() {
        String[] dog = {"poodle", "husky", "pit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dog);
        spinnerSub.setAdapter(adapter);
    }

    // Custom toast message
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
