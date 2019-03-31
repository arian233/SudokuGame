package com.cmpt276.lota.sudoku.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cmpt276.lota.sudoku.R;

// This creates the start screen
public class StartScreen extends AppCompatActivity {
    private Button start;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start_screen);
        start = (Button)findViewById(R.id.start_button);
        start.setBackground(getResources().getDrawable(R.drawable.buttons));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial();
            }
        });

    }
    public void initial(){
        Intent intent = new Intent(this, SudokuActivity.class);
        startActivity(intent);

    }

}
