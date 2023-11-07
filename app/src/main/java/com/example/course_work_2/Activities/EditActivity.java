package com.example.course_work_2.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.course_work_2.Database.AppDatabase;
import com.example.course_work_2.Models.Hike;
import com.example.course_work_2.R;
import com.example.course_work_2.adapters.HikeAdapter;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    private EditText hikeName;
    private EditText hikeLocation;
    private EditText hikeDate;
    private EditText lengthInput;
    RadioGroup parkingRadioGroup;
    Spinner difficultySpinner;
    EditText descriptionInput;
    private Button updateButton;
    List<Hike> hikes;

    private AppDatabase appDatabase;
    private Hike hike;
    HikeAdapter hikeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        hikeName = findViewById(R.id.name_input);
        hikeLocation = findViewById(R.id.location_input);
        hikeDate = findViewById(R.id.date_input);
        parkingRadioGroup = findViewById(R.id.radioGroup);
        lengthInput = findViewById(R.id.length_input);
        difficultySpinner = findViewById(R.id.spinner_difficulty);
        descriptionInput = findViewById(R.id.editTextTextMultiLine);
        updateButton = findViewById(R.id.update_button);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hike")
                .allowMainThreadQueries()
                .build();

        int hikeId = getIntent().getIntExtra("hikeId", -1);
        if (hikeId != -1) {
            hike = appDatabase.hikeDao().getHikeById(hikeId);
            hikeName.setText(hike.name);
            hikeLocation.setText(hike.location);
            hikeDate.setText(hike.date);
            lengthInput.setText(String.valueOf(hike.distance));
            descriptionInput.setText(hike.description);
            if (hike.isParking) {
                parkingRadioGroup.check(R.id.radioButton3);
            } else {
                parkingRadioGroup.check(R.id.radioButton2);
            }

            switch (hike.difficulty) {
                case "Easy":
                    difficultySpinner.setSelection(0);
                    break;
                case "Medium":
                    difficultySpinner.setSelection(1);
                    break;
                case "Hard":
                    difficultySpinner.setSelection(2);
                    break;
            }
        }
        updateButton.setOnClickListener(v -> {
            updateHike();
            finish();
        });
    }

    private void updateHike() {
        hike.name = hikeName.getText().toString();
        hike.location = hikeLocation.getText().toString();
        hike.date = hikeDate.getText().toString();
        hike.isParking = parkingRadioGroup.getCheckedRadioButtonId() == R.id.radioButton3;
        hike.distance = Integer.parseInt(lengthInput.getText().toString());
        hike.difficulty = difficultySpinner.getSelectedItem().toString();
        hike.description = descriptionInput.getText().toString();

        appDatabase.hikeDao().update(hike);
        Toast.makeText(this, "Hike updated", Toast.LENGTH_SHORT).show();
        int position = getIntent().getIntExtra("position", -1);
        if(position != -1) {
            hikes.set(position, hike);
            hikeAdapter.notifyItemChanged(position);
        }
    }
}