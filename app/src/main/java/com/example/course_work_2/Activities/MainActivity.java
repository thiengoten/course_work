package com.example.course_work_2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.course_work_2.Database.AppDatabase;
import com.example.course_work_2.Models.Hike;
import com.example.course_work_2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private AppDatabase appDatabase;

    public static Intent getIntent(Context applicationContext) {
        return new Intent(applicationContext, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hike")
                .allowMainThreadQueries()
                .build();

        Button addHike = findViewById(R.id.add_button);
        //save the hike to the database
        addHike.setOnClickListener(v -> {
            if(!isAllFieldsEmpty()) {
                saveHike();
                clearFields();
            } else Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.add);
    }

    private void clearFields() {
        EditText hikeName = findViewById(R.id.name_input);
        EditText hikeLocation = findViewById(R.id.location_input);
        EditText hikeDate = findViewById(R.id.date_input);
        RadioGroup parkingRadioGroup = findViewById(R.id.radioGroup);
        EditText lengthInput = findViewById(R.id.length_input);
        Spinner difficultySpinner = findViewById(R.id.spinner_difficulty);
        EditText descriptionInput = findViewById(R.id.editTextTextMultiLine);

        hikeName.setText("");
        hikeLocation.setText("");
        hikeDate.setText("");
        parkingRadioGroup.clearCheck();
        lengthInput.setText("");
        difficultySpinner.setSelection(0);
        descriptionInput.setText("");
    }

    private void saveHike() {
        EditText hikeName = findViewById(R.id.name_input);
        EditText hikeLocation = findViewById(R.id.location_input);
        EditText hikeDate = findViewById(R.id.date_input);
        RadioGroup parkingRadioGroup = findViewById(R.id.radioGroup);
        EditText lengthInput = findViewById(R.id.length_input);
        Spinner difficultySpinner = findViewById(R.id.spinner_difficulty);
        EditText descriptionInput = findViewById(R.id.editTextTextMultiLine);

        String name = hikeName.getText().toString();
        String location = hikeLocation.getText().toString();
        String date = hikeDate.getText().toString();
        boolean isParking = parkingRadioGroup.getCheckedRadioButtonId() == R.id.radioButton3;
        int distance = Integer.parseInt(lengthInput.getText().toString());
        String difficulty = difficultySpinner.getSelectedItem().toString();
        String description = descriptionInput.getText().toString();

        Hike hike = new Hike();
        hike.name = name;
        hike.location = location;
        hike.date = date;
        hike.isParking = isParking;
        hike.distance = distance;
        hike.difficulty = difficulty;
        hike.description = description;


        long hikeId = appDatabase.hikeDao().insert(hike);
        Toast.makeText(this, "Hike added with id: " + hikeId, Toast.LENGTH_SHORT).show();
    }

    private boolean isAllFieldsEmpty() {
        EditText hikeName = findViewById(R.id.name_input);
        EditText hikeLocation = findViewById(R.id.location_input);
        EditText hikeDate = findViewById(R.id.date_input);
        RadioGroup parkingRadioGroup = findViewById(R.id.radioGroup);
        EditText lengthInput = findViewById(R.id.length_input);

        return hikeName.getText().toString().isEmpty() &&
                hikeLocation.getText().toString().isEmpty() &&
                hikeDate.getText().toString().isEmpty() &&
                parkingRadioGroup.getCheckedRadioButtonId() == -1 &&
                lengthInput.getText().toString().isEmpty();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.home_page) {
            startActivity(Home.getIntent(getApplicationContext()));
            overridePendingTransition(0, 0);
        } else if(item.getItemId() == R.id.search_test) {
            startActivity(Search.getIntent(getApplicationContext()));
            overridePendingTransition(0, 0);
        } else return item.getItemId() == R.id.add;
        return false;
    }
}