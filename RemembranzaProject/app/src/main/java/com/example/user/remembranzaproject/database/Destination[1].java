package com.example.user.remembranzaproject.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Destination {
    @PrimaryKey(autoGenerate = true)
    private long id_destination;
    @ColumnInfo(name = "destination_name")
    private String name;
    @ColumnInfo(name = "x_coordination")
    private float x_coordinate;
    @ColumnInfo(name = "y_coordination")
    private float y_coordinate;

    public Destination(String name, float x_coordinate, float y_coordinate){
        this.name = name;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public long getId_destination() {
        return id_destination;
    }

    public String getName() {
        return name;
    }

    public float getX_coordinate() {
        return x_coordinate;
    }

    public float getY_coordinate() {
        return y_coordinate;
    }

    public void setId_destination(long id_destination) {
        this.id_destination = id_destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX_coordinate(float x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public void setY_coordinate(float y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
}
