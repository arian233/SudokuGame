package com.cmpt276.lota.sudoku.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.cmpt276.lota.sudoku.R;

/**
 * This class show a gif when user wins the game
 */
public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_gif);
    }
}
