package com.example.app2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class PruebasActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // Attributes
    private Button threeXThree;
    private Button forXFor;
    private Button fiveXFive;
    private Button sixXSix;

    private GridLayout gridMain;
    private Drawable items;


    private int swipeMinDistance;
    private int swipeThresholdVelocity;
    private GestureDetectorCompat gestureDetectorCompat;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////


        this.threeXThree = findViewById(R.id.threeXThree);
        this.forXFor = findViewById(R.id.fourXFour);
        this.fiveXFive = findViewById(R.id.fiveXFive);
        this.sixXSix = findViewById(R.id.sixXSix);

        this.gridMain = findViewById(R.id.gridMain);
        this.items = getDrawable(R.drawable.item);

        this.setSwipeMinDistance(80);
        this.setSwipeThresholdVelocity(50);
        this.setGestureDetectorCompat(new GestureDetectorCompat(getApplicationContext(), this));


        ////////////////////////////////////////////////////////////////////////////////////////////////////////


        this.threeXThree.setOnClickListener(view -> {
            this.createGridLayout(3);
            this.changeVisibilityOfGridLayout(true);
        });

        this.forXFor.setOnClickListener(view -> {
            this.createGridLayout(4);
            this.changeVisibilityOfGridLayout(true);
        });

        this.fiveXFive.setOnClickListener(view -> {
            this.createGridLayout(5);
            this.changeVisibilityOfGridLayout(true);
        });

        this.sixXSix.setOnClickListener(view -> {
            this.createGridLayout(6);
            this.changeVisibilityOfGridLayout(true);
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Change visibility of Grid Layout
     *
     * @param choice User choice (true --> VISIBLE) | (false --> INVISIBLE)
     */
    public void changeVisibilityOfGridLayout(boolean choice) {
        if (choice) {
            this.gridMain.setVisibility(View.VISIBLE);
        } else {
            this.gridMain.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * Configure GridLayout and its TextViews
     *
     * @param sizeColumnsAndRows Size chosen by the user
     */
    public void createGridLayout(int sizeColumnsAndRows) {

        // Setting size of GridLayout
        this.atributesGridLayout(sizeColumnsAndRows);

        // Fill GridLayout with TextViews
        for (int i = 0; i < sizeColumnsAndRows; i++) {
            for (int j = 0; j < sizeColumnsAndRows; j++) {
                this.createTextViewIntoGridLayout(j, i, sizeColumnsAndRows);
            }
        }
    }


    /**
     * Configure columns and rows of the GridLayout
     *
     * @param sizeColumnsAndRows Size chosen by the user
     */
    public void atributesGridLayout(int sizeColumnsAndRows) {
        this.gridMain.setColumnCount(sizeColumnsAndRows);
        this.gridMain.setRowCount(sizeColumnsAndRows);
    }


    /**
     * Fill all the boxes of the GridLayout with TextViews
     *
     * @param column             Column of GridLayout to create the new TextView
     * @param row                Row of GridLayout to create the new TextView
     * @param sizeColumnsAndRows Size chosen by the user
     */
    public void createTextViewIntoGridLayout(int column, int row, int sizeColumnsAndRows) {

        // TextView to add
        TextView textView = createTextViewToGridLayout(sizeColumnsAndRows);

        // LayoutParams of GridLayout
        GridLayout.LayoutParams layoutParams = createLayoutParamsToGridLayout(column, row, sizeColumnsAndRows);

        this.gridMain.addView(textView, layoutParams);
    }


    /**
     * Create TextView with attributes to fill GridLayout
     *
     * @param sizeColumnsAndRows Size chosen by the user
     * @return TextView configured
     */
    public TextView createTextViewToGridLayout(int sizeColumnsAndRows) {
        TextView textView = new TextView(this);

        textView.setText("1");
        textView.setBackground(this.items);
        textView.setGravity(Gravity.CENTER);


        textView.setTextSize(this.choiceTextSize(sizeColumnsAndRows));

        return textView;
    }


    /**
     * Choose the size of the text according to the size chosen by the user
     *
     * @param sizeColumnsAndRows Size chosen by the user
     * @return Size of the text
     */
    public int choiceTextSize(int sizeColumnsAndRows) {
        Map<Integer, Integer> textSize = Map.of(
                3, 50,
                4, 40,
                5, 30,
                6, 20
        );

        return textSize.get(sizeColumnsAndRows);
    }


    /**
     * Create LayoutParams with attributes to TextView, to fill GridLayout
     *
     * @param column             Column of GridLayout to create the new TextView
     * @param row                Row of GridLayout to create the new TextView
     * @param sizeColumnsAndRows Size chosen by the user
     * @return LayoutParams to add the new TextView
     */
    public GridLayout.LayoutParams createLayoutParamsToGridLayout(int column, int row, int sizeColumnsAndRows) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(row, 1f),
                GridLayout.spec(column, 1f)
        );

        int margins = this.choiceMarginSize(sizeColumnsAndRows);
        layoutParams.setMargins(margins, margins, margins, margins);

        return layoutParams;
    }


    /**
     * Choose the margin of the TextViews according to the size chosen by the user
     *
     * @param sizeColumnsAndRows Size chosen by the user
     * @return Margin of the TextView
     */
    public int choiceMarginSize(int sizeColumnsAndRows) {
        Map<Integer, Integer> marginSize = Map.of(
                3, 25,
                4, 20,
                5, 15,
                6, 10
        );

        return marginSize.get(sizeColumnsAndRows);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Controller of Flings from user
     *
     * @param stateDirection Direction choice of user
     */
    public void flingDirectionsControler(StateDirection stateDirection) {
        Map<StateDirection, String> direction = Map.of(
                StateDirection.TOP, "TOP",
                StateDirection.BOTTOM, "BOTTOM",
                StateDirection.LEFT, "LEFT",
                StateDirection.RIGHT, "RIGHT"
        );

        Toast.makeText(getApplicationContext(), direction.get(stateDirection), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Detect direction from Fling
        boolean response = false;

        float distanceLeft = e1.getX() - e2.getX();
        float distanceRight = e2.getX() - e1.getX();
        float distanceTop = e1.getY() - e2.getY();
        float distanceBottom = e2.getY() - e1.getY();


        if (Math.abs(distanceLeft) > Math.abs(distanceTop)) {
            if (this.flingOptimal(distanceLeft, velocityX)) {
                this.flingDirectionsControler(StateDirection.LEFT);
                response = true;

            } else if (this.flingOptimal(distanceRight, velocityX)) {
                this.flingDirectionsControler(StateDirection.RIGHT);
                response = true;

            }
        } else {
            if (this.flingOptimal(distanceTop, velocityY)) {
                this.flingDirectionsControler(StateDirection.TOP);
                response = true;

            } else if (this.flingOptimal(distanceBottom, velocityY)) {
                this.flingDirectionsControler(StateDirection.BOTTOM);
                response = true;
            }
        }

        return response;
    }


    /**
     * Check if the distance and velocity from method onFling() are optimal to make a action
     *
     * @param distance Previously calculated distance
     * @param velocity Velocity from method onFling()
     * @return If is optimal to make a action
     */
    public boolean flingOptimal(float distance, float velocity) {
        return (distance > this.getSwipeMinDistance() && Math.abs(velocity) > this.getSwipeThresholdVelocity());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Necessary to detect the method onFling
        if (this.gestureDetectorCompat.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Getters and Setters
    public Button getThreeXThree() {
        return threeXThree;
    }

    public void setThreeXThree(Button threeXThree) {
        this.threeXThree = threeXThree;
    }

    public Button getForXFor() {
        return forXFor;
    }

    public void setForXFor(Button forXFor) {
        this.forXFor = forXFor;
    }

    public Button getFiveXFive() {
        return fiveXFive;
    }

    public void setFiveXFive(Button fiveXFive) {
        this.fiveXFive = fiveXFive;
    }

    public Button getSixXSix() {
        return sixXSix;
    }

    public void setSixXSix(Button sixXSix) {
        this.sixXSix = sixXSix;
    }

    public GridLayout getGridMain() {
        return gridMain;
    }

    public void setGridMain(GridLayout gridMain) {
        this.gridMain = gridMain;
    }

    public Drawable getItems() {
        return items;
    }

    public void setItems(Drawable items) {
        this.items = items;
    }

    public int getSwipeMinDistance() {
        return swipeMinDistance;
    }

    public void setSwipeMinDistance(int swipeMinDistance) {
        this.swipeMinDistance = swipeMinDistance;
    }

    public int getSwipeThresholdVelocity() {
        return swipeThresholdVelocity;
    }

    public void setSwipeThresholdVelocity(int swipeThresholdVelocity) {
        this.swipeThresholdVelocity = swipeThresholdVelocity;
    }

    public GestureDetectorCompat getGestureDetectorCompat() {
        return gestureDetectorCompat;
    }

    public void setGestureDetectorCompat(GestureDetectorCompat gestureDetectorCompat) {
        this.gestureDetectorCompat = gestureDetectorCompat;
    }
}