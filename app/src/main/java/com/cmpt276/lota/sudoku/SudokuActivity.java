package com.cmpt276.lota.sudoku;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.widget.Chronometer;
import android.widget.Toast;
import java.util.ArrayList;

public class SudokuActivity extends Activity {

    private final int mPUZZLESIZE = 9;
    private final int mPUZZLETOTALSIZE = 81;
    private final int mFONTSIZE = 8;
    private PuzzleGenerator generator;
    private CheckResult mCheckResult;
    private Chronometer timer;

    private Language mPuzzle[][];//puzzle
    private static String lan1[];//Language one
    private static String lan2[];//Language two
    private static String lanDialog[];//Language to be shown in dialog

    private static int dialogChosenIndex = -1;//-1 is nothing to be chosen
    private int switchLanguageFlag = 1;//1 is 1st lan, -1 is second lan
    private int highlightedButton = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        //set timer
        timer = findViewById(R.id.timer);
        timer.setFormat("time passed: %s");
        timer.start();

        //initializations
        generator = new PuzzleGenerator();
        mPuzzle = generator.generatePuzzle();
        lan1 = generator.getLanOne();
        lan2 = generator.getLanTwo();
        lanDialog = lan2;
        mCheckResult = new CheckResult();

        //initialize checkResultButton
        Button checkResultButton = findViewById(R.id.check_result_button);
        checkResultButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        checkResultButton.setTextSize(mFONTSIZE);
        checkResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                if(mCheckResult.checkResult(mPuzzle)){
                    toast =Toast.makeText(SudokuActivity.this,R.string.Complete_toast,Toast.LENGTH_SHORT);
                    timer.stop();
                }else{
                    toast =Toast.makeText(SudokuActivity.this,R.string.Fail_toast,Toast.LENGTH_SHORT);
                }
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        //initialize switch language Button
        Button switchButton = findViewById(R.id.switch_button);
        switchButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        switchButton.setTextSize(mFONTSIZE);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguageFlag *= -1;
                switchLanguageInDialog();

                for (int j = 0; j < mPUZZLETOTALSIZE; j++){
                    Button tobeChangedButton = findViewById(j);
                    int x = j % mPUZZLESIZE;
                    int y = j / mPUZZLESIZE;
                    if(switchLanguageFlag == 1){
                        if(mPuzzle[y][x].getFlag() == -1){
                            tobeChangedButton.setText(mPuzzle[y][x].getLanguageOne());
                        }else if(mPuzzle[y][x].getFlag() == 1){
                            tobeChangedButton.setText(mPuzzle[y][x].getLanguageTwo());
                        }
                    }else{
                        if(mPuzzle[y][x].getFlag() == -1){
                            tobeChangedButton.setText(mPuzzle[y][x].getLanguageTwo());
                        }else if(mPuzzle[y][x].getFlag() == 1){
                            tobeChangedButton.setText(mPuzzle[y][x].getLanguageOne());
                        }
                    }

                }
            }
        });

        //initialize gridLayout 9 rows and 9 columns
        GridLayout gridLayout = findViewById(R.id.grid_layout);
        gridLayout.setColumnCount(mPUZZLESIZE);
        gridLayout.setRowCount(mPUZZLESIZE);
        //get screen size
        //int width = getResources().getDisplayMetrics().widthPixels;
        //width/=9;
        for (int i = 0; i < mPUZZLETOTALSIZE; i++) {
            final Button button = new Button(this);
            int x = i % mPUZZLESIZE;
            int y = i / mPUZZLESIZE;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.rowSpec = GridLayout.spec(y, 1f);
            params.columnSpec = GridLayout.spec(x, 1f);
            button.setTextSize(mFONTSIZE);
            button.setTextColor(Color.parseColor("#282626"));

            // set button style
            if (mPuzzle[y][x].getFlag() != -1) {
                button.setBackground(getResources().getDrawable(R.drawable.emptybutton));
                button.setText( "" );
            }else{
                button.setBackground(getResources().getDrawable(R.drawable.presetbutton));
                button.setText( mPuzzle[y][x].getLanguageOne() );

                //float size = presetbutton.getTextSize();
                //Log.d("jialic", "presetbutton width:"+width+"  text size"+ size);
            }

            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonId = button.getId();
                    int x = buttonId % mPUZZLESIZE;
                    int y = buttonId / mPUZZLESIZE;
                    //change highlighted buttons back to origin color
                    if(highlightedButton != -1)
                        changeHighlightBack(highlightedButton);
                    //when pressing preset grid, give hint; otherwise show dialog
                    if(mPuzzle[y][x].getFlag() == -1){
                        if(mPuzzle[y][x].getFlag() == -1){
                            String str;
                            if (switchLanguageFlag == 1 )
                                str = mPuzzle[y][x].getLanguageTwo();
                            else
                                str = mPuzzle[y][x].getLanguageOne();
                            Toast toast = Toast.makeText(SudokuActivity.this,str,Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }else{
                        showRadioDialog(buttonId);
                        //highlight corresponding row and column
                        highlightButton(buttonId);
                        highlightedButton = buttonId;
                    }
                }
            });

            button.setGravity(Gravity.CENTER);
            params.setMargins(2, 2, 2, 2);
            gridLayout.addView(button, params);
        }
    }

    private void showRadioDialog(int id){

        final int buttonId = id;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.dialog_radio_text));
        alertDialog.setIcon(R.mipmap.ic_launcher_round);

        alertDialog.setItems(lanDialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogChosenIndex = which;
                        changePuzzle(buttonId);
                        dialogChosenIndex = -1;
                    }
                }).create();
        alertDialog.show();
    }

    public void changePuzzle(int id){
        int x = id % mPUZZLESIZE;
        int y = id / mPUZZLESIZE;
        Language saved = mPuzzle[y][x];
        mPuzzle[y][x] = new Language(dialogChosenIndex +1, lan1[dialogChosenIndex], lan2[dialogChosenIndex],1);

        if (!mCheckResult.checkValid(mPuzzle,y,x)){
            //invalid input, found repetition
            mPuzzle[y][x] = saved;
            Toast toast =Toast.makeText(SudokuActivity.this,R.string.FoundRepeat_toast,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return;
        }
        Button tobeChangedButton = findViewById(id);
        if(switchLanguageFlag == 1){
            tobeChangedButton.setText(lan2[dialogChosenIndex]);
        }else{
            tobeChangedButton.setText(lan1[dialogChosenIndex]);
        }
    }

    public void switchLanguageInDialog(){
        if(switchLanguageFlag == 1){
            lanDialog = lan2;
        }else{
            lanDialog = lan1;
        }
    }

    public void highlightButton(int id){
        int x = id % mPUZZLESIZE;
        int y = id / mPUZZLESIZE;
        for(int i = 1; i <= mPUZZLESIZE;i++){
            Button tobeChangedButton = findViewById((i-1)*9+x);
            tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.highlightbutton));
            tobeChangedButton = findViewById(i-1+9*y);
            tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.highlightbutton));
        }
    }

    public void changeHighlightBack(int id){
        int x = id % mPUZZLESIZE;
        int y = id / mPUZZLESIZE;
        for(int i = 1; i<=mPUZZLESIZE; i++){
            int id2 = (i-1)*9+x;
            Button tobeChangedButton = findViewById(id2);
            int x2 = id2 % mPUZZLESIZE;
            int y2 = id2 / mPUZZLESIZE;
            if (mPuzzle[y2][x2].getFlag() != -1) {
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.emptybutton));
            }else{
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
            }

            id2 = i-1+9*y;
            x2 = id2 % mPUZZLESIZE;
            y2 = id2 / mPUZZLESIZE;
            tobeChangedButton = findViewById(id2);
            if (mPuzzle[y2][x2].getFlag() != -1) {
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.emptybutton));
            }else{
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
            }
        }
    }


}
