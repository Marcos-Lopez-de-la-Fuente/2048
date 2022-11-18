package com.example.app2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PruebasActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private Button nextTestZone;


    // Attributes
    private GridLayout gridParent;
    private Button threeXThree;
    private Button forXFor;
    private Button fiveXFive;
    private Button sixXSix;


    private GridLayout gridMain;
    private TextView[][] gridFrontend;
    private Integer[][] gridBackend;
    private boolean gridChosen = false;
    private Drawable items;


    private int swipeMinDistance;
    private int swipeThresholdVelocity;
    private GestureDetectorCompat gestureDetectorCompat;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Exercices", "PruebasActivity.java");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.nextTestZone = findViewById(R.id.nextTestZone);


        this.gridParent = findViewById(R.id.gridParent);

        this.threeXThree = findViewById(R.id.threeXThree);
        this.forXFor = findViewById(R.id.fourXFour);
        this.fiveXFive = findViewById(R.id.fiveXFive);
        this.sixXSix = findViewById(R.id.sixXSix);

        this.items = getDrawable(R.drawable.item);

        this.setSwipeMinDistance(80);
        this.setSwipeThresholdVelocity(50);
        this.setGestureDetectorCompat(new GestureDetectorCompat(getApplicationContext(), this));


        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.nextTestZone.setOnClickListener(view ->
                this.startActivity(new Intent(this, PruebasActivity.class))
        );


        this.threeXThree.setOnClickListener(view -> {
            this.generateGridLayout(3);
        });

        this.forXFor.setOnClickListener(view -> {
            this.generateGridLayout(4);
            this.generateANumberRandom(4);

        });

        this.fiveXFive.setOnClickListener(view -> {
            this.generateGridLayout(5);
        });

        this.sixXSix.setOnClickListener(view -> {
            this.generateGridLayout(6);
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void generateANumberRandom(int sizeGrid) {

        boolean numberCreate = false;

        ArrayList<ArrayList<Integer>> arrNumbersNull = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < this.getGridBackend().length; i++) {
            for (int j = 0; j < this.getGridBackend()[i].length; j++) {
                if (this.getGridBackend()[i][j] == null) {
                    ArrayList<Integer> newNumberNull = new ArrayList<Integer>();
                    newNumberNull.add(i);
                    newNumberNull.add(j);

                    arrNumbersNull.add(newNumberNull);
                }
            }
        }

        int newNumberRandom = new Random().nextInt(arrNumbersNull.size() - 1);
        int rowRandom = arrNumbersNull.get(newNumberRandom).get(0);
        int columnRandom = arrNumbersNull.get(newNumberRandom).get(1);


        if (this.getGridBackend()[rowRandom][columnRandom] == null) {
            this.getGridBackend()[rowRandom][columnRandom] = 2;

            this.getGridFrontend()[rowRandom][columnRandom].setText("2");

            numberCreate = true;
        }
    }


    public void synchronizeBackendToFronted() {
        for (int i = 0; i < this.getGridBackend().length; i++) {
            for (int j = 0; j < this.getGridBackend()[i].length; j++) {
                if (this.getGridBackend()[i][j] != null) {
                    this.getGridFrontend()[i][j].setText(String.valueOf(this.getGridBackend()[i][j]));
                } else {
                    this.getGridFrontend()[i][j].setText("");
                }
            }
        }

    }


    /**
     * Configure GridLayout and its TextViews
     *
     * @param sizeColumnsAndRows Size chosen by the user
     */
    public void generateGridLayout(int sizeColumnsAndRows) {

        this.createGridLayoutAndParams();


        // Setting size of GridLayout
        this.atributesGridLayout(sizeColumnsAndRows);

        // Fill GridLayout with TextViews
        this.fillGridLayout(sizeColumnsAndRows);
    }

    public void createGridLayoutAndParams() {

        this.createGridLayout();

        GridLayout.LayoutParams layoutParams = this.createGridLayoutParams();

        this.gridParent.addView(this.gridMain, layoutParams);
    }

    public void createGridLayout() {
        if (this.gridChosen) {
            this.gridParent.removeView(this.gridMain);
        }
        this.gridChosen = true;

        this.gridMain = new GridLayout(this);
        this.gridMain.setBackground(getDrawable(R.drawable.backgrid));
    }

    public GridLayout.LayoutParams createGridLayoutParams() {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(5, 1),
                GridLayout.spec(0, 3)
        );
        layoutParams.width = GridLayout.LayoutParams.MATCH_PARENT;
        layoutParams.setMargins(0, 20, 0, 0);

        return layoutParams;
    }


    public void fillGridLayout(int sizeColumnsAndRows) {
        this.setGridFrontend(new TextView[sizeColumnsAndRows][sizeColumnsAndRows]);
        this.setGridBackend(new Integer[sizeColumnsAndRows][sizeColumnsAndRows]);


        for (int i = 0; i < sizeColumnsAndRows; i++) {
            for (int j = 0; j < sizeColumnsAndRows; j++) {
                this.getGridBackend()[i][j] = null;
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
        this.getGridFrontend()[row][column] = createTextViewToGridLayout(sizeColumnsAndRows);

        // LayoutParams of GridLayout
        GridLayout.LayoutParams layoutParams = createLayoutParamsToGridLayout(column, row, sizeColumnsAndRows);

        this.gridMain.addView(this.getGridFrontend()[row][column], layoutParams);
    }


    /**
     * Create TextView with attributes to fill GridLayout
     *
     * @param sizeColumnsAndRows Size chosen by the user
     * @return TextView configured
     */
    public TextView createTextViewToGridLayout(int sizeColumnsAndRows) {
        TextView textView = new TextView(this);

        textView.setBackground(this.items);
        textView.setGravity(Gravity.CENTER);


        // Por ahora no usaré el siguiente método (Cuando toque estilizar el programa jugaré con este método y el choicePaddingText())
        //textView.setTextSize(this.choiceTextSize(sizeColumnsAndRows));

        int textPadding = this.choicePaddingText(sizeColumnsAndRows);
        textView.setPadding(0, textPadding, 0, textPadding);


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
                3, 45,
                4, 40,
                5, 30,
                6, 20
        );

        return textSize.get(sizeColumnsAndRows);
    }

    public int choicePaddingText(int sizeColumnsAndRows) {
        Map<Integer, Integer> paddingText = Map.of(
                3, 100,
                4, 70,
                5, 50,
                6, 40
        );

        return paddingText.get(sizeColumnsAndRows);
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

        // Toast.makeText(getApplicationContext(), direction.get(stateDirection), Toast.LENGTH_SHORT).show();

        this.moveNumbers(stateDirection);
    }

    public void moveNumbers(StateDirection stateDirection) {
        Map<StateDirection, String> direction = Map.of(
                StateDirection.TOP, "TOP",
                StateDirection.BOTTOM, "BOTTOM",
                StateDirection.LEFT, "LEFT",
                StateDirection.RIGHT, "RIGHT"
        );

        switch (direction.get(stateDirection)) {
            case "TOP":
                for (int i = 0; i < this.getGridBackend().length; i++) {
                    for (int j = 0; j < this.getGridBackend()[i].length; j++) {
                        if (this.getGridBackend()[i][j] != null) {

                            int counter = 0;
                            while (counter < i) {
                                if (this.getGridBackend()[counter][j] == this.getGridBackend()[i][j]) {
                                    this.getGridBackend()[counter][j] += this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = i;

                                } else if (this.getGridBackend()[counter][j] == null) {
                                    this.getGridBackend()[counter][j] = this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = i;
                                }

                                counter++;
                            }


                        }
                    }
                }
                this.synchronizeBackendToFronted();
                this.generateANumberRandom(this.gridBackend.length);
                break;
            case "BOTTOM":
                for (int i = this.getGridBackend().length - 1; i >= 0; i--) {
                    for (int j = 0; j < this.getGridBackend()[i].length; j++) {
                        if (this.getGridBackend()[i][j] != null) {

                            int counter = this.getGridBackend().length - 1;
                            while (counter > i) {
                                if (this.getGridBackend()[counter][j] == this.getGridBackend()[i][j]) {
                                    this.getGridBackend()[counter][j] += this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = i;

                                } else if (this.getGridBackend()[counter][j] == null) {
                                    this.getGridBackend()[counter][j] = this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = i;
                                }

                                counter--;
                            }


                        }
                    }
                }
                this.synchronizeBackendToFronted();
                this.generateANumberRandom(this.gridBackend.length);
                break;
            case "LEFT":
                for (int i = 0; i < this.getGridBackend().length; i++) {
                    for (int j = 0; j < this.getGridBackend()[i].length; j++) {
                        if (this.getGridBackend()[i][j] != null) {

                            int counter = 0;
                            while (counter < j) {
                                if (this.getGridBackend()[i][counter] == this.getGridBackend()[i][j]) {
                                    this.getGridBackend()[i][counter] += this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = i;

                                } else if (this.getGridBackend()[i][counter] == null) {
                                    this.getGridBackend()[i][counter] = this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = j;
                                }

                                counter++;
                            }


                        }
                    }
                }
                this.synchronizeBackendToFronted();
                this.generateANumberRandom(this.gridBackend.length);
                break;
            case "RIGHT":
                for (int i = 0; i < this.getGridBackend().length; i++) {
                    for (int j = this.getGridBackend()[i].length - 1; j >= 0; j--) {
                        if (this.getGridBackend()[i][j] != null) {

                            int counter = this.getGridBackend()[i].length - 1;
                            while (counter > j) {
                                if (this.getGridBackend()[i][counter] != null && this.getGridBackend()[i][counter] == this.getGridBackend()[i][j]) {
                                    Log.i("Exercices", this.getGridBackend()[i][counter] + " |||| " + this.getGridBackend()[i][j]);
                                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    //////////////////// IGUAL ES NECESARIO AÑADIR EL && AL IF
                                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    this.getGridBackend()[i][counter] += this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = i;

                                } else if (this.getGridBackend()[i][counter] == null) {
                                    this.getGridBackend()[i][counter] = this.getGridBackend()[i][j];
                                    this.getGridBackend()[i][j] = null;

                                    counter = j;
                                }

                                counter--;
                            }


                        }
                    }
                }
                this.synchronizeBackendToFronted();
                this.generateANumberRandom(this.gridBackend.length);
                break;
        }


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

    public Button getNextTestZone() {
        return nextTestZone;
    }

    public void setNextTestZone(Button nextTestZone) {
        this.nextTestZone = nextTestZone;
    }

    public GridLayout getGridParent() {
        return gridParent;
    }

    public void setGridParent(GridLayout gridParent) {
        this.gridParent = gridParent;
    }

    public Integer[][] getGridBackend() {
        return gridBackend;
    }

    public void setGridBackend(Integer[][] gridBackend) {
        this.gridBackend = gridBackend;
    }

    public boolean isGridChosen() {
        return gridChosen;
    }

    public void setGridChosen(boolean gridChosen) {
        this.gridChosen = gridChosen;
    }

    public TextView[][] getGridFrontend() {
        return gridFrontend;
    }

    public void setGridFrontend(TextView[][] gridFrontend) {
        this.gridFrontend = gridFrontend;
    }
}