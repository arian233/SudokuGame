package com.cmpt276.lota.sudoku.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import com.cmpt276.lota.sudoku.R;
import com.cmpt276.lota.sudoku.model.WordListLab;

public class WelcomeActivity extends Activity {
    private WordListLab wordListLab = WordListLab.get(WelcomeActivity.this);
    private Button start;
    private Button setting;
    private Dialog dialog;
    private Button wordsListsButton;
    private Button addWordsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        start = findViewById(R.id.initial_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int cx = start.getWidth() / 2;
//                int cy = start.getHeight() / 2;
//                float radius = start.getWidth();
//                Animator anim = ViewAnimationUtils
//                        .createCircularReveal(start, cx, cy, radius, 0);
//                anim.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        start.setVisibility(View.INVISIBLE);
//                    }
//                });
//                anim.start();
                initial();
            } });

        setting = findViewById(R.id.setting_button);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioDialog();
            } });

        //initialize wordsListsButton
        wordsListsButton = this.findViewById(R.id.word_list_button);
        wordsListsButton.setPadding(10,10,10,10);
        wordsListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,WordsListsActivity.class );
                startActivity(intent);
            }
        });

        //initialize AddWordsButton
        addWordsButton = this.findViewById(R.id.add_words_button);
        addWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, InputWordsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initial() {
        Intent intent = new Intent(this, SudokuActivity.class);
        startActivity(intent);
    }

    /**
     * give a Dialog for user to input setting
     */
    private void showRadioDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.setting_dialog);
        dialog.show();

        Button confirmBtn = dialog.findViewById(R.id.confirm_btn_dialog);
        Button cancelBtn = dialog.findViewById(R.id.cancel_btn_dialog);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find the radiobutton
                RadioButton radioLan = dialog.findViewById(R.id.radioChToEng);
                RadioButton radioLanDefault = dialog.findViewById(R.id.radioEngToCh);
                RadioButton radioMode = dialog.findViewById(R.id.radioListenMode);
                RadioButton radioModeDefault = dialog.findViewById(R.id.radioTextMode);
                RadioButton radioSize4 = dialog.findViewById(R.id.radioSizeMode4);
                RadioButton radioSize6 = dialog.findViewById(R.id.radioSizeMode6);
                RadioButton radioSize12 = dialog.findViewById(R.id.radioSizeMode12);
                RadioButton radioSizeDefault = dialog.findViewById(R.id.radioSizeMode9);

                //set the settings
                if(radioLan.isChecked()){
                    wordListLab.setSwitchLanguageFlag(-1);
                }else if(radioLanDefault.isChecked()){
                    wordListLab.setSwitchLanguageFlag(1);
                }

                if(radioMode.isChecked()){
                    wordListLab.setListeningModeFlag(1);
                }else if(radioModeDefault.isChecked()){
                    wordListLab.setListeningModeFlag(-1);
                }

                if(radioSize4.isChecked()){
                    wordListLab.setPuzzleSize(4);
                }else if(radioSize6.isChecked()){
                    wordListLab.setPuzzleSize(6);
                }else if(radioSize12.isChecked()){
                    wordListLab.setPuzzleSize(12);
                }else if(radioSizeDefault.isChecked()){
                    wordListLab.setPuzzleSize(9);
                }

                dialog.dismiss();
            }

        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
