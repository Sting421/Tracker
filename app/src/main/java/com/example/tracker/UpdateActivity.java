package com.example.tracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import DB.MyDBHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText patientName, patientAge, patientGender, patientCondition;
    Button update_button, delete_button;
    String id, name, age, gender, condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        patientName = findViewById(R.id.upName);
        patientAge = findViewById(R.id.upAge);
        patientGender = findViewById(R.id.upGender);
        patientCondition = findViewById(R.id.upCondition);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDBHelper myDB = new MyDBHelper(UpdateActivity.this);
                name = patientName.getText().toString().trim();
                age = patientAge.getText().toString().trim();
                gender = patientGender.getText().toString().trim();
                condition = patientCondition.getText().toString().trim();
                myDB.updateData(id, name, age, gender, condition);
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("age") && getIntent().hasExtra("gender") && getIntent().hasExtra("condition")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            age = getIntent().getStringExtra("age");
            gender = getIntent().getStringExtra("gender");
            condition = getIntent().getStringExtra("condition");

            //Setting Intent Data
            patientName.setText(name);
            patientAge.setText(age);
            patientGender.setText(gender);
            patientCondition.setText(condition);

            Log.d("stev", name+" "+age+" "+gender+" "+condition);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper myDB = new MyDBHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
