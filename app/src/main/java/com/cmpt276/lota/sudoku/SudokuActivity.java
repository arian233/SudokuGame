package com.cmpt276.lota.sudoku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class SudokuActivity extends AppCompatActivity {
    private WordListLab wordListLab = WordListLab.get(SudokuActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.sudoku_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.sudoku_fragment_container);

        if (fragment == null) {
            fragment =  new SudokuFragment();
            fm.beginTransaction()
                    .add(R.id.sudoku_fragment_container, fragment)
                    .commit();
        }
    }

}