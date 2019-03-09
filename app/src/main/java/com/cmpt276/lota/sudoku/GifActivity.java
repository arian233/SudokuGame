package com.cmpt276.lota.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_gif);
    }
}
