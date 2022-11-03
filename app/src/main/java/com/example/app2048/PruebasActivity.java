package com.example.app2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class PruebasActivity extends AppCompatActivity {

    private Button forXFor;
    private GridLayout gridMain;
    private Drawable items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        this.forXFor = findViewById(R.id.forXfor);
        this.gridMain = findViewById(R.id.gridMain);
        this.items = getDrawable(R.drawable.item);


        // Si pulsan el botón
        this.forXFor.setOnClickListener(view -> {

            // Se settean el tamaño del GridLayout
            this.atributesGridLayout(4, 4);

            // Se rellenan todas las casillas posibles dentro
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    this.createTextViewIntoGridLayout(j, i);
                }
            }

        });

    }


    // Método para settear las columnas y filas que tendrá el GridLayout
    public void atributesGridLayout(int columns, int rows) {
        this.gridMain.setColumnCount(columns);
        this.gridMain.setRowCount(rows);
    }


    // Método para crear e introducir los TextView dentro del GridLayout
    public void createTextViewIntoGridLayout(int column, int row) {
        TextView textView = new TextView(this);

        textView.setText("1");
        textView.setBackground(this.items);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(40);


        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams
                (GridLayout.spec(row, 1f),
                        GridLayout.spec(column, 1f)
                );


        int margins = 20;

        layoutParams.setMargins(margins, margins, margins, margins);

        textView.setLayoutParams(layoutParams);


        this.gridMain.addView(textView);


    }


    public void createTextViewToGridLayout() {
        
    }

}