package com.rogmax.amirjaber.taskrogmax22;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rogmax.amirjaber.taskrogmax22.helpers.DatabaseHelper;
import com.rogmax.amirjaber.taskrogmax22.models.PersonView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class ViewPeopleActivity extends AppCompatActivity {

    private static final String LOG = "CreateP";
    private static List<PersonView> pView = new ArrayList<>();
    private static final int DELETE = 0;
    static int clickedItemIndex;
    ListView pListView;
    DatabaseHelper dbHelper;
    ArrayAdapter<PersonView> pAdapter;

    // List Getter
    public static List<PersonView> getP() {
        return pView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_people);
        /* Stetho */
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        // Initializing person list view and database
        pListView = (ListView) findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(getApplicationContext());


        // Populate list if people in database are not 0
        if (dbHelper.getPeopleCount() != 0) {
            getP().clear();
            getP().addAll(dbHelper.getAllPeople());
        }

        registerForContextMenu(pListView);
        pListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickedItemIndex = position;
                Log.d(LOG, "onItemClick: You have clicked on item number " + clickedItemIndex);
                return false;
            }
        });

        //populate the person list
        populatePList();

    }

    // Create adapter for person list
    private class PListAdapter extends ArrayAdapter<PersonView> {

        private PListAdapter() {
            super(ViewPeopleActivity.this, R.layout.listview_p, getP());
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_p, parent, false);

            PersonView currentP = getP().get(position);

            TextView name = view.findViewById(R.id.personName);
            name.setText(currentP.getName());
            TextView type = view.findViewById(R.id.personAge);
            type.setText(currentP.getAge());
            TextView subType = view.findViewById(R.id.petsName);
            subType.setText(currentP.getPetName());

            return view;

        }

    }

    // Populate list view
    private void populatePList() {
        Log.d(LOG, "CreateP: Displaying data in ListView");
        pAdapter = new PListAdapter();
        pListView.setAdapter(pAdapter);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE:
                dbHelper.deletePerson(getP().get(clickedItemIndex));
                getP().remove(clickedItemIndex);
                pAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    // Add context menu items
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_edit);
        menu.setHeaderTitle("Person Delete");
        menu.add(Menu.NONE, DELETE, Menu.NONE, "Delete Pet");
    }
}
