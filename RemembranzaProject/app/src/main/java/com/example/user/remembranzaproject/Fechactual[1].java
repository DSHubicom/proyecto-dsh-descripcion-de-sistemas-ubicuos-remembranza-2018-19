package com.example.user.remembranzaproject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by user on 6/04/18.
 */

public class Fechactual {
    private int dayOfMonth;
    private int month;
    private int year;

    public Fechactual(){
        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
        this.dayOfMonth =calendarNow.get(Calendar.DAY_OF_MONTH);
        this.month = calendarNow.get(Calendar.MONTH);
        this.year = calendarNow.get(Calendar.YEAR);
    }

    public String getFechaActual(){
        return String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth);
    }
}
