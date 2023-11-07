package com.example.course_work_2.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.course_work_2.Models.Hike;
import com.example.course_work_2.dao.HikeDao;

@Database(entities = {Hike.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}
