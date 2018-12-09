package com.example.user.remembranzaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.user.remembranzaproject.database.AppDatabase;
import com.example.user.remembranzaproject.database.Destination;
import com.example.user.remembranzaproject.database.Memento;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreateRoute extends AppCompatActivity {

    private String fragment_tag;
    private List<Memento> mementos;
    private ArrayList<String> mementos_list;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        //Actives back button in action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Start route");
        ab.setDisplayHomeAsUpEnabled(true);

        Spinner destinations = findViewById(R.id.destinations);
        List<String> destinations_str = new ArrayList<>();
        try {
            List<Destination> destinationList = new getAll_destinations().execute().get();

            for(Destination aux : destinationList){
                destinations_str.add(aux.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, destinations_str);
        destinations.setAdapter(adapter);

        try {
            this.mementos = new getAll_mementos().execute().get();
            this.mementos_list = new ArrayList<>();
            for(Memento aux : this.mementos){
                this.mementos_list.add(aux.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        this.listView = findViewById(R.id.mementos);
        this.adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.item_text, this.mementos_list);
        this.listView.setAdapter(this.adapter);

        this.fragment_tag = getIntent().getExtras().get("fragment").toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(CreateRoute.this, MainActivity.class);
                intent.putExtra("fragment", this.fragment_tag);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class getAll_destinations extends AsyncTask<Void, Void, List<Destination>> {

        @Override
        protected List<Destination> doInBackground(Void... voids) {
            AppDatabase appDatabase = AppDatabase.getDatabase(CreateRoute.this);
            return appDatabase.destinationDao().getAll();
        }
    }

    class getAll_mementos extends AsyncTask<Void, Void, List<Memento>> {

        @Override
        protected List<Memento> doInBackground(Void... voids) {
            AppDatabase appDatabase = AppDatabase.getDatabase(CreateRoute.this);
            return appDatabase.mementoDao().getAll();
        }
    }
}
