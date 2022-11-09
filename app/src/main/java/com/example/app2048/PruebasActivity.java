package com.example.app2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class PruebasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Exercices", "MainActivity.java");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);
    }
}