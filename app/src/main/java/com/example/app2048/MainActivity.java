package com.example.app2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button goToTestZone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.goToTestZone = findViewById(R.id.goToTestZone);


        this.goToTestZone.setOnClickListener(view ->
                this.startActivity(new Intent(this, PruebasActivity.class))
        );

    }
}