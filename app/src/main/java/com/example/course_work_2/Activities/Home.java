package com.example.course_work_2.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.course_work_2.Database.AppDatabase;
import com.example.course_work_2.Models.Hike;
import com.example.course_work_2.R;
import com.example.course_work_2.adapters.HikeAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private HikeAdapter hikeAdapter;
    List<Hike> hikes;

    Button deleteAllButton;

    public static Intent getIntent(Context applicationContext) {
        return new Intent(applicationContext, Home.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hike")
                .allowMainThreadQueries()
                .build();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hikes = appDatabase.hikeDao().getAllHikes();
        hikeAdapter = new HikeAdapter(hikes, appDatabase);
        recyclerView.setAdapter(hikeAdapter);

        deleteAllButton = findViewById(R.id.delete_all);
        deleteAllButton.setOnClickListener(v -> {
            hikeAdapter.deleteAll(hikes, appDatabase);
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(MainActivity.getIntent(getApplicationContext()));
            overridePendingTransition(0, 0);
        } else if (item.getItemId() == R.id.search_test) {
            startActivity(Search.getIntent(getApplicationContext()));
            overridePendingTransition(0, 0);
        } else return item.getItemId() == R.id.home_page;
        return false;
    }
}