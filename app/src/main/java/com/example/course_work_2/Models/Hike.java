package com.example.course_work_2.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hikes")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "hike_name")
    public String name;

    public String location;

    public String description;

    public String date;

    @ColumnInfo(name = "is_parking")
    public Boolean isParking;

    public int distance;

    public String difficulty;
}
