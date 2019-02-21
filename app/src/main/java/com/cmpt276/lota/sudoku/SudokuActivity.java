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

    private final int mPuzzleSize = 9;

    private final int textsize = 8;
    private PuzzleGenerator generator;
    private static int dialogChosen = -1;

    ArrayList<Language> mChoseButtonTexts = new ArrayList<Language>();
    private Language mPuzzle[][];
    private static String lan1[];
    private static String lan2[];
    private static String lanDialog[];

    private CheckResult mCheckResult;
    private int switchLanguageFlag = 1;//1 is 1st lan, -1 is second lan

    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        timer = findViewById(R.id.timer);
        timer.setFormat("time passed: %s");
        timer.start();

        generator = new PuzzleGenerator();
        mChoseButtonTexts = generator.generateLanguage(mChoseButtonTexts);//initialize Language
        mPuzzle = generator.generatePuzzle();
        mCheckResult = new CheckResult();
        lan1 = generator.getLanOne();
        lan2 = generator.getLanTwo();
        lanDialog = lan2;

        GridLayout gridLayout = findViewById(R.id.grid_layout);
        // 9 rows and 9 columns gridlayout
        gridLayout.setColumnCount(mPuzzleSize);
        gridLayout.setRowCount(mPuzzleSize);
        //get screen size
        //int width = getResources().getDisplayMetrics().widthPixels;
        //width/=9;

        Button checkResultButton = findViewById(R.id.check_result_button);
        checkResultButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        checkResultButton.setTextSize(8);
        checkResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                //to check result
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

        Button switchButton = findViewById(R.id.switch_button);
        switchButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        switchButton.setTextSize(8);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to switch language
                switchLanguageFlag *= -1;
                switchLanguageInDialog();

                if(switchLanguageFlag == 1){
                    for (int j = 0; j < 81; j++){
                        Button tobeChangedButton = findViewById(j);
                        if(mPuzzle[j / 9][j % 9].getFlag() == -1){
                            tobeChangedButton.setText(mPuzzle[j / 9][j % 9].getLanguageOne());
                        }else if(mPuzzle[j / 9][j % 9].getFlag() == 1){
                            tobeChangedButton.setText(mPuzzle[j / 9][j % 9].getLanguageTwo());
                        }
                    }
                }else{
                    for (int j = 0; j < 81; j++){
                        Button tobeChangedButton = findViewById(j);
                        if(mPuzzle[j / 9][j % 9].getFlag() == -1){
                            tobeChangedButton.setText(mPuzzle[j / 9][j % 9].getLanguageTwo());
                        }else if(mPuzzle[j / 9][j % 9].getFlag() == 1){
                            tobeChangedButton.setText(mPuzzle[j / 9][j % 9].getLanguageOne());
                        }
                    }
                }
            }
        });

        for (int i = 0; i < 81; i++) {
            final Button button = new Button(this);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;

            // 设置行列下标，和比重
            params.rowSpec = GridLayout.spec(i / 9, 1f);
            params.columnSpec = GridLayout.spec(i % 9, 1f);
            button.setTextSize(textsize);
            button.setTextColor(Color.parseColor("#282626"));

            // 字体颜色
            if (mPuzzle[i / 9][i % 9].getFlag() != -1) {
                button.setBackground(getResources().getDrawable(R.drawable.emptybutton));

                button.setText( "" );

            }else if (mPuzzle[i / 9][i % 9].getFlag() == -1) {
                button.setBackground(getResources().getDrawable(R.drawable.presetbutton));
                //presetbutton.setBackgroundColor(Color.parseColor("#A8D2D4"));

                button.setText( mPuzzle[i / 9][i % 9].getLanguageOne() );

                //float size = presetbutton.getTextSize();
                //Log.d("jialic", "presetbutton width:"+width+"  text size"+ size);

            }

            button.setId(i);
            final CharSequence str = button.getText();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonId = button.getId();
                    int x = buttonId % 9;
                    int y = buttonId / 9;
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
                        dialogChosen = which;
                        changePuzzle(buttonId);
                        dialogChosen = -1;
                    }
                }).create();
        alertDialog.show();

    }

    public void changePuzzle(int id){
        int x = id % 9;
        int y = id / 9;
        Language saved = mPuzzle[y][x];
        mPuzzle[y][x] = new Language(dialogChosen+1, lan1[dialogChosen], lan2[dialogChosen],1);

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
            tobeChangedButton.setText(lan2[dialogChosen]);
        }else{
            tobeChangedButton.setText(lan1[dialogChosen]);
        }

    }

    public void switchLanguageInDialog(){
        if(switchLanguageFlag == 1){
            lanDialog = lan2;
        }else{
            lanDialog = lan1;
        }
    }

}
