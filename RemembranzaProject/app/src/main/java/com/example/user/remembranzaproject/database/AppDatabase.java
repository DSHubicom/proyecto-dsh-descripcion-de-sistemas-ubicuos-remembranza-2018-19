package com.example.user.remembranzaproject.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Destination.class, Memento.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static Context myContext;

    public abstract DestinationDao destinationDao();
    public abstract MementoDao mementoDao();

    public static AppDatabase getDatabase(Context context){
        if(instance== null){
            myContext=context;
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app.db").build();
        }
        return instance;
    }

}
