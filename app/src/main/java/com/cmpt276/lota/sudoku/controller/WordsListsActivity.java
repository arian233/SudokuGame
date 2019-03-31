package com.cmpt276.lota.sudoku.controller;

import android.support.v4.app.Fragment;

public class WordsListsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WordsListsFragment();
    }
}


