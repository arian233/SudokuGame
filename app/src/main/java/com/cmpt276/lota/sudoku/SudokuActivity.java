package com.cmpt276.lota.sudoku;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.widget.Chronometer;
import android.widget.Toast;
import java.util.Locale;

public class SudokuActivity extends Activity implements TextToSpeech.OnInitListener{

    private static final String KEY_dialogChosenIndex = "dialogChosenIndex";
    private static final String KEY_switchLanguageFlag = "switchLanguageFlag";
    private static final String KEY_highlightedButton = "highlightedButton";

    private final int mPUZZLESIZE = 9;
    private final int mPUZZLETOTALSIZE = 81;
    private final int mFONTSIZE = 8;
    private PuzzleGenerator generator;
    private CheckResult mCheckResult;
    private Chronometer timer;
    private TextToSpeech textToSpeech;

    private Language mPuzzle[][];//puzzle
    private static String lan1[];//Language one
    private static String lan2[];//Language two
    private static String lanDialog[];//Language to be shown in dialog

    private static int dialogChosenIndex = -1;//-1 is nothing to be chosen
    private int switchLanguageFlag = 1;//1 is 1st lan, -1 is second lan
    private int listeningModeFlag = -1;//-1 is normal mode, 1 is listening mode
    private int highlightedButton = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        if (savedInstanceState != null) {
            //timer.start();
        }

        //set timer
        timer = findViewById(R.id.timer);
        timer.setFormat("time passed: %s");
        timer.start();

        //initializations
        generator = new PuzzleGenerator();
        mPuzzle = generator.generateGrid();
        lan1 = generator.getLanOne();
        lan2 = generator.getLanTwo();
        lanDialog = lan2;
        mCheckResult = new CheckResult();

        textToSpeech = new TextToSpeech(this, this);


        //initialize listeningModeButton
        Button listeningModeButton = findViewById(R.id.listening_mode_button);
        listeningModeButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        listeningModeButton.setTextSize(2*mFONTSIZE);
        listeningModeButton.setPadding(10,10,10,10);
        listeningModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeningModeFlag *= -1;
                if(listeningModeFlag == 1){
                    changeButtobTextsforListening();
                }else {
                    changeButtonTextforSwitchLanguage();
                }
            }
        });
        //initialize erase button
        Button eraseButton = findViewById(R.id.erase_button);
        eraseButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        eraseButton.setTextSize(2*mFONTSIZE);
        eraseButton.setPadding(10,10,10,10);
        eraseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //eraseAnswer();
            }
        });

        //initialize checkResultButton
        Button checkResultButton = findViewById(R.id.check_result_button);
        checkResultButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        checkResultButton.setTextSize(2*mFONTSIZE);
        checkResultButton.setPadding(10,10,10,10);
        checkResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        //initialize switch language Button
        Button switchButton = findViewById(R.id.switch_button);
        switchButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
        switchButton.setTextSize(2*mFONTSIZE);
        switchButton.setPadding(10,10,10,10);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguageFlag *= -1;
                switchLanguageInDialog();
                changeButtonTextforSwitchLanguage();
            }
        });

        //initialize gridLayout 9 rows and 9 columns
        GridLayout gridLayout = findViewById(R.id.grid_layout);
        gridLayout.setColumnCount(mPUZZLESIZE);
        gridLayout.setRowCount(mPUZZLESIZE);
        initializeGridLayout(gridLayout);
        //get screen size
        //int width = getResources().getDisplayMetrics().widthPixels;
        //width/=9;
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

    public void changeButtobTextsforListening(){
        for(int i=0; i<mPUZZLETOTALSIZE; i++){
            Button tobeChangedButton = findViewById(i);
            int x = i % mPUZZLESIZE;
            int y = i / mPUZZLESIZE;
            if(mPuzzle[y][x].getFlag() != 0){
                tobeChangedButton.setText(String.valueOf(mPuzzle[y][x].getNumber()));
            }
        }
    }


    public void checkAnswer(){
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

    public void changeButtonTextforSwitchLanguage(){
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

    public void initializeGridLayout(GridLayout gridLayout){
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
                    if(listeningModeFlag == -1){
                        //in normal mode
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
                    }else{
                        //in listening mode
                        if(mPuzzle[y][x].getFlag() != 0){
                            if (textToSpeech != null && !textToSpeech.isSpeaking()) {
                                //set to default
                                textToSpeech.setPitch(1.0f);
                                //set to default
                                textToSpeech.setSpeechRate(1.0f);
                                textToSpeech.speak(mPuzzle[y][x].getLanguageTwo(), TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    }
                }
            });

            button.setGravity(Gravity.CENTER);
            params.setMargins(2, 2, 2, 2);
            gridLayout.addView(button, params);
        }
    }

    /**
     * initialize TextToSpeech
     * @param status: required
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, R.string.Fail_sound_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //timer.pause();
        //savedInstanceState.putInt(KEY_switchLanguageFlag, switchLanguageFlag);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}