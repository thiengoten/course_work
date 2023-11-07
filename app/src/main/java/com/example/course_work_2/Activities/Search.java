package com.example.course_work_2.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.course_work_2.R;

public class Search extends AppCompatActivity {

    public static Intent getIntent(Context applicationContext) {
        return new Intent(applicationContext, Search.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}