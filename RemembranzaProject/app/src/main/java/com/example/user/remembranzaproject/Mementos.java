package com.example.user.remembranzaproject;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.user.remembranzaproject.database.AppDatabase;
import com.example.user.remembranzaproject.database.Memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Mementos extends Fragment {

    private View rootView;
    private List<String> mementos_list;
    private ArrayAdapter<String> adapter;
    private TextView item;
    private ListView listView;
    private List<Memento> mementos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_mementos, container, false);

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


        this.listView = this.rootView.findViewById(R.id.mementos);
        this.adapter = new ArrayAdapter<String>(this.rootView.getContext(), R.layout.listview_item, R.id.item_text, this.mementos_list);
        this.listView.setAdapter(this.adapter);

        return this.rootView;
    }

    class getAll_mementos extends AsyncTask<Void, Void, List<Memento>> {

        @Override
        protected List<Memento> doInBackground(Void... voids) {
            AppDatabase appDatabase = AppDatabase.getDatabase(rootView.getContext());
            return appDatabase.mementoDao().getAll();
        }
    }
}
