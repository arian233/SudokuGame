package com.cmpt276.lota.sudoku.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.cmpt276.lota.sudoku.R;
import com.cmpt276.lota.sudoku.model.WordListLab;

public class WelcomeActivity extends Activity {
    private WordListLab wordListLab = WordListLab.get(WelcomeActivity.this);
    private Button start;
    private Button setting;
    private Dialog dialog;
    private Button wordsListsButton;
    private Button addWordsButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        start = findViewById(R.id.initial_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        dialog.setCancelable(true);
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
                RadioButton radioTheme = dialog.findViewById(R.id.lightTheme);
                RadioButton radioTheme2 = dialog.findViewById(R.id.darkTheme);

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

                if(radioTheme.isChecked()){
                    wordListLab.setThemeFlag(-1);
                }else if(radioTheme2.isChecked()){
                    wordListLab.setThemeFlag(1);
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

        textView = dialog.findViewById(R.id.difficulty_seekbar);
        SeekBar seekBar = dialog.findViewById(R.id.seekBar2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String str = "";
                if(progress == 0){
                    str="very easy";
                    wordListLab.setNumberOfEmptyCell(0);
                }else if(progress == 1){
                    str="easy";
                    wordListLab.setNumberOfEmptyCell(1);
                }else if(progress == 2){
                    str="medium";
                    wordListLab.setNumberOfEmptyCell(4);
                }else if(progress == 3){
                    str="hard";
                    wordListLab.setNumberOfEmptyCell(6);
                }else if(progress == 4){
                    str="very hard";
                    wordListLab.setNumberOfEmptyCell(8);
                }else if(progress == 5){
                    str="master";
                    wordListLab.setNumberOfEmptyCell(10);
                }

                textView.setText("Choose a difficulty: "+ str);
            }
        });

    }

}
