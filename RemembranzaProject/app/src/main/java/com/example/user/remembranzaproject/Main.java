package com.example.user.remembranzaproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

public class Main extends Fragment {

    private View rootView;
    private CalendarView calendario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Se obtiene la fecha actual
        Fechactual fechactual = new Fechactual();
        final String[] fechaSeleccionada = {fechactual.getFechaActual()};
        Log.w("main", "-> "+ fechaSeleccionada[0]);

        //Selector de fecha
        calendario = rootView.findViewById(R.id.calendar);
        calendario.setMaxDate(System.currentTimeMillis());

        /*calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fechaSeleccionada[0] = String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth);

            }
        });*/

        return this.rootView;
    }
}
