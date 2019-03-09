package com.cmpt276.lota.sudoku;

import android.support.v4.app.Fragment;

public class WordsListsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new WordsListsFragment();
    }
}


