package com.cmpt276.lota.sudoku;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SudokuActivity extends Activity {

    private MyView customView;
    //View gameView = (View) findViewById(R.id.game_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyView customView= new MyView(this.getBaseContext());
        this.setContentView(customView);




        //gameView.addView(customView);
        //setContentView(R.layout.activity_sudoku);
        //customView = findViewById(R.id.MyView);



    }


}
