package com.cmpt276.lota.sudoku;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SudokuActivity extends AppCompatActivity {

    MyView customView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sudoku);
        customView= new MyView(this.getBaseContext());
        this.setContentView(customView);

        //customView = findViewById(R.id.my_view);


    }


}
