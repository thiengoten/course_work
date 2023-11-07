package com.example.course_work_2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_work_2.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insert(Hike hike);

    @Query("SELECT * FROM hikes ORDER BY id DESC")
    List<Hike> getAllHikes();

    @Delete
    void delete(Hike hike);

    @Update
    void update(Hike hike);

    @Query("SELECT * FROM hikes WHERE id = :id")
    Hike getHikeById(int id);

    @Delete
    void deleteAll(List<Hike> hikes);
}
