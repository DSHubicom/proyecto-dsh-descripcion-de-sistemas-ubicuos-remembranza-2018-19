package com.example.user.remembranzaproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MementoDao {
    @Query("SELECT * FROM memento")
    List<Memento> getAll();

    @Query("SELECT * FROM memento WHERE :id_memento")
    Memento getById(long id_memento);

    @Insert
    public long insertDestination(Memento memento);
}
