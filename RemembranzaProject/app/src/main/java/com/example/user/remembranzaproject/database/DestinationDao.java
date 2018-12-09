package com.example.user.remembranzaproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DestinationDao {
    @Query("SELECT * FROM destination")
    public List<Destination> getAll();

    @Query("SELECT * FROM destination WHERE :id_destination")
    public Destination getById(long id_destination);

    @Insert
    public long insertDestination(Destination destination);
}
