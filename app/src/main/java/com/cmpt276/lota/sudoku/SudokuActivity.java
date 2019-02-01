package com.cmpt276.lota.sudoku;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SudokuActivity extends AppCompatActivity {

    MyView customView;
    View gameView = (View) findViewById(R.id.game_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customView= new MyView(this.getBaseContext());
        //this.setContentView(customView);

        //customView = findViewById(R.id.my_view);


        gameView.addView(customView);
        setContentView(R.layout.activity_sudoku);

//        TextView valueTV = new TextView(this);
//        valueTV.setText("hallo hallo");
//        valueTV.setId(5);
//        valueTV.setLayoutParams(new LayoutParams(
//                LayoutParams.FILL_PARENT,
//                LayoutParams.WRAP_CONTENT));
//
//        ((LinearLayout) linearLayout).addView(valueTV);


    }


}
