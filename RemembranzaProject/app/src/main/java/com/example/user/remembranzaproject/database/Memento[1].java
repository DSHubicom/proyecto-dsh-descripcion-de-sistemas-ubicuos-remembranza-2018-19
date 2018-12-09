package com.example.user.remembranzaproject.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Memento {
    @PrimaryKey(autoGenerate = true)
    private long id_memento;
    @ColumnInfo(name = "memento_name")
    private String name;
    @ColumnInfo(name = "x_coordination")
    private float x_coordinate;
    @ColumnInfo(name = "y_coordination")
    private float y_coordinate;

    public Memento(String name, float x_coordinate, float y_coordinate){
        this.name = name;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public long getId_memento() {
        return id_memento;
    }

    public void setId_memento(long id_memento) {
        this.id_memento = id_memento;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(float x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public float getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(float y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
}
