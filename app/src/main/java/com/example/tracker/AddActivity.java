package com.example.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import DB.MyDBHelper;

public class AddActivity extends AppCompatActivity {


    EditText tfName, tfAge, tfGender,tfCondition;
    Button add_button;
    ImageButton addBcakButton;
    MainActivity main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tfName = findViewById(R.id.tfName);
        tfAge = findViewById(R.id.tfAge);
        tfGender = findViewById(R.id.tfGender);
        tfCondition = findViewById(R.id.tfCondition);
        add_button = findViewById(R.id.add_button);

        addBcakButton = findViewById(R.id.addBackBtn);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper myDB = new MyDBHelper(AddActivity.this);
                myDB.addPatient(tfName.getText().toString().trim(),
                        tfAge.getText().toString().trim(),
                        tfGender.getText().toString().trim(),
                        tfCondition.getText().toString().trim());
                Intent intent = new Intent(AddActivity.this, Dashboard.class);
                startActivity(intent);

                finish();

            }

        });
        addBcakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, Dashboard.class);
                startActivity(intent);

                finish();

            }

        });
    }
}