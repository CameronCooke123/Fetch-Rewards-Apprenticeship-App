package com.example.fetchrewardsapprenticeshipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDisplayDataActivity(View v)
    {
        //switch to new activity
        Intent i = new Intent(this, DisplayDataActivity.class);
        startActivity(i);
    }
}