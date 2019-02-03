package com.cmpt276.lota.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;


public class SudokuActivity extends Activity {
    private MyView customView;
    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MyView customView= new MyView(this);
        //setContentView(customView);

        setContentView(R.layout.activity_sudoku);
        customView = findViewById(R.id.MyCustomView);
        timer = findViewById(R.id.chronometer);
        timer.setFormat("time: %s");
        timer.start();
    }

}
