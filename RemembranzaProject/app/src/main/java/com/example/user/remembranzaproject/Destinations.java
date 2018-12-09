package com.example.user.remembranzaproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.remembranzaproject.database.AppDatabase;
import com.example.user.remembranzaproject.database.Destination;
import com.example.user.remembranzaproject.database.Memento;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Destinations extends Fragment {

    private View rootView;
    private List<Destination> destinations;
    private ArrayList<String> destination_str;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_destinations, container, false);

        try {
            this.destinations = new getAll_destinations().execute().get();
            this.destination_str = new ArrayList<>();
            for(Destination aux : this.destinations){
                this.destination_str.add(aux.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        this.listView = this.rootView.findViewById(R.id.destinations);
        this.adapter = new ArrayAdapter<String>(this.rootView.getContext(), R.layout.listview_item, R.id.item_text, this.destination_str);
        this.listView.setAdapter(this.adapter);

        return this.rootView;
    }

    class getAll_destinations extends AsyncTask<Void, Void, List<Destination>> {

        @Override
        protected List<Destination> doInBackground(Void... voids) {
            AppDatabase appDatabase = AppDatabase.getDatabase(rootView.getContext());
            return appDatabase.destinationDao().getAll();
        }
    }
}
