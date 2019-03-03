package com.cmpt276.lota.sudoku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class WordsListsActivity extends SingleFragmentActivity {



//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Intent i = getIntent();
//        Bundle b= i.getBundleExtra("B");
//
//        WordListLab wordListLab= (WordListLab) b.getSerializable("S");
//    }


    @Override
    protected Fragment createFragment() {
        return new WordsListsFragment();
    }


}


