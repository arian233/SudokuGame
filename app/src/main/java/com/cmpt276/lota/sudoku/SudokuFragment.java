package com.cmpt276.lota.sudoku;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import 	android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

public class SudokuFragment extends Fragment implements TextToSpeech.OnInitListener {

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
    private int erasedButtonId = -1;//to erase cell

    private WordListLab wordListLab = WordListLab.getWordListLab();
    private int familiarity[] = new int[mPUZZLESIZE];
    private View layout;

    private ImageButton refreshButton;
    private Button eraseButton;
    private Button checkResultButton;
    private Button switchButton;
    private Button wordsListsButton;
    private ImageButton addWordsButton;
    private Button listeningModeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        generator = new PuzzleGenerator();
        mPuzzle = generator.generateGrid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.activity_sudoku, null);//android:configChanges="orientation|screenSize|keyboardHidden"
        initialPuzzle();
        initial();
        if (listeningModeFlag == 1){
            listeningModeControl();
        }else{
            changeButtonTextforSwitchLanguage();
        }
        return layout;
    }

    /**
     * run the code when first run, and when user click refresh button
     */
    public void initialPuzzle(){
        //initializations
        lan1 = generator.getLanOne();
        lan2 = generator.getLanTwo();
        lanDialog = lan2;
        mCheckResult = new CheckResult();
    }

    /**
     * run the code when user click refresh button
     */
    public void initialForRefresh(){
        switchLanguageFlag = 1;

        for (int i = 0; i < mPUZZLETOTALSIZE; i++) {
            TextView tobeChangedButton = layout.findViewById(i);
            int x = i % mPUZZLESIZE;
            int y = i / mPUZZLESIZE;

            if (mPuzzle[y][x].getFlag() != -1) {
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.emptybutton));
                tobeChangedButton.setText("");
            } else {
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
                tobeChangedButton.setText(mPuzzle[y][x].getLanguageOne());
            }
        }
        if(listeningModeFlag != -1){
            changeButtobTextsforListening();
        }
    }

    /**
     * separate below code b/c those codes only need to be ran once, don't need to be ran when user click refresh button
     */
    public void initial(){
        //set timer
        timer = layout.findViewById(R.id.timer);
        timer.setFormat("time passed: %s");
        timer.start();

        textToSpeech = new TextToSpeech(getActivity(), this);

        //initialize refreshButton
        refreshButton = layout.findViewById(R.id.refresh_button);
        refreshButton.setBackground(getResources().getDrawable(R.drawable.buttons));
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str[][] = new String[3][2];
                for(int i = 0; i < 3; i++){//for familiarity
                    int maxIndex = 0;//find max value's index
                    for (int j = 0; j < familiarity.length; j++) {
                        maxIndex = familiarity[j] > familiarity[maxIndex] ? j : maxIndex;
                    }
                    if(familiarity[maxIndex] != 0){
                        str[i][0] = lan1[maxIndex];
                        str[i][1] = lan2[maxIndex];
                    }else{
                        str[i][0] = "";//if there's no/ not enough unfamiliar words, then set unfamiliar words list to empty
                        str[i][1] = "";
                    }
                    familiarity[maxIndex] = 0;
                }
                wordListLab.setNotFamiliarWord(str);
                generator = new PuzzleGenerator();
                mPuzzle = generator.generateGrid();
                initialPuzzle();
                initialForRefresh();
            }
        });

        //initialize erase button
        eraseButton = layout.findViewById(R.id.erase_button);
        eraseButton.setBackground(getResources().getDrawable(R.drawable.buttons));
        eraseButton.setTextSize(2*mFONTSIZE);
        eraseButton.setPadding(10,10,10,10);
        eraseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int x = erasedButtonId % mPUZZLESIZE;
                int y = erasedButtonId / mPUZZLESIZE;
                dialogChosenIndex= -1;
                Toast toast = Toast.makeText(getActivity(),R.string.Erase_toast,Toast.LENGTH_SHORT);
                if ( x > -1 && y > -1){
                    mPuzzle[y][x] = new Language(dialogChosenIndex +1, lan1[dialogChosenIndex+1], lan2[dialogChosenIndex+1],0);
                    TextView tobeChangedButton = layout.findViewById(erasedButtonId);
                    tobeChangedButton.setText("");
                }
                else{
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });

        //initialize checkResultButton
        checkResultButton = layout.findViewById(R.id.check_result_button);
        checkResultButton.setBackground(getResources().getDrawable(R.drawable.buttons));
        checkResultButton.setTextSize(2*mFONTSIZE);
        checkResultButton.setPadding(10,10,10,10);
        checkResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        //initialize switch language Button
        switchButton = layout.findViewById(R.id.switch_button);
        switchButton.setBackground(getResources().getDrawable(R.drawable.buttons));
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

        //initialize wordsListsButton
        wordsListsButton = layout.findViewById(R.id.word_list_button);
        wordsListsButton.setBackground(getResources().getDrawable(R.drawable.buttons));
        wordsListsButton.setTextSize(2*mFONTSIZE);
        wordsListsButton.setPadding(10,10,10,10);
        wordsListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WordsListsActivity.class );
                startActivity(intent);
            }
        });

        //initialize AddWordsButton
        addWordsButton = layout.findViewById(R.id.add_words_button);
        addWordsButton.setBackground(getResources().getDrawable(R.drawable.buttons));
        addWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InputWordsActivity.class);
                startActivity(intent);
            }
        });


        //initialize listeningModeButton
        listeningModeButton = layout.findViewById(R.id.listening_mode_button);
        listeningModeButton.setBackground(getResources().getDrawable(R.drawable.buttons));
        listeningModeButton.setTextSize(2*mFONTSIZE);
        listeningModeButton.setPadding(10,10,10,10);
        listeningModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeningModeFlag *= -1;
                listeningModeControl();
            }
        });

        //initialize gridLayout 9 rows and 9 columns
        GridLayout gridLayout = layout.findViewById(R.id.grid_layout);
        gridLayout.setColumnCount(mPUZZLESIZE);
        gridLayout.setRowCount(mPUZZLESIZE);
        initializeGridLayout(gridLayout);
    }

    /**
    * gibe a Dialog for user to input words
    */
    private void showRadioDialog(int id){
        final int buttonId = id;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

    /**
     * handle the situation if user inputs a repeat word
     */
    public void changePuzzle(int id){
        int x = id % mPUZZLESIZE;
        int y = id / mPUZZLESIZE;
        Language saved = mPuzzle[y][x];//backup the origin word in that grid
        mPuzzle[y][x] = new Language(dialogChosenIndex +1, lan1[dialogChosenIndex], lan2[dialogChosenIndex],1);

        if (!mCheckResult.checkValid(mPuzzle,y,x)){
            //invalid input, found repetition
            mPuzzle[y][x] = saved;//if found repetition, change the grid back to backup word
            Toast toast =Toast.makeText(getActivity(),R.string.FoundRepeat_toast,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return;
        }
        TextView tobeChangedButton = layout.findViewById(id);
        if(switchLanguageFlag == 1){
            tobeChangedButton.setText(lan2[dialogChosenIndex]);
        }else{
            tobeChangedButton.setText(lan1[dialogChosenIndex]);
        }
    }

    /**
     * when user switches language, the texts in the dialog changes too
     */
    public void switchLanguageInDialog(){
        if(switchLanguageFlag == 1){
            lanDialog = lan2;
        }else{
            lanDialog = lan1;
        }
    }

    /**
     * highlight row and column
     */
    public void highlightButton(int id){
        int x = id % mPUZZLESIZE;
        int y = id / mPUZZLESIZE;
        for(int i = 1; i <= mPUZZLESIZE;i++){
            TextView tobeChangedButton = layout.findViewById((i-1)*9+x);
            tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.highlightbutton));
            tobeChangedButton = layout.findViewById(i-1+9*y);
            tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.highlightbutton));
        }
    }

    /**
     * change highlihted grid back to normal color
     */
    public void changeHighlightBack(int id){
        int x = id % mPUZZLESIZE;
        int y = id / mPUZZLESIZE;
        for(int i = 1; i<=mPUZZLESIZE; i++){
            int id2 = (i-1)*9+x;
            TextView tobeChangedButton = layout.findViewById(id2);
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
            tobeChangedButton = layout.findViewById(id2);
            if (mPuzzle[y2][x2].getFlag() != -1) {
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.emptybutton));
            }else{
                tobeChangedButton.setBackground(getResources().getDrawable(R.drawable.presetbutton));
            }
        }
    }

    /**
     * change texts to numbers in listening mode
     */
    public void changeButtobTextsforListening(){
        int randomListeningIndex[] = new int[mPUZZLESIZE];//to prevent cheating in listening mode
        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        Random random=new Random();

        for(int i = 0; i<mPUZZLESIZE ; ){
            int randomInt = random.nextInt( mPUZZLESIZE );
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                randomListeningIndex[i] = randomInt;
                i++;
            }else{
                continue;
            }
        }
        for(int i=0; i<mPUZZLETOTALSIZE; i++){
            TextView tobeChangedButton = layout.findViewById(i);
            int x = i % mPUZZLESIZE;
            int y = i / mPUZZLESIZE;
            if(mPuzzle[y][x].getFlag() != 0){
                tobeChangedButton.setText(String.valueOf(randomListeningIndex[mPuzzle[y][x].getNumber()-1] + 1));
            }
        }
    }

    /**
     * to check final answer and save 3 most unfamiliar words
     */
    public void checkAnswer(){
        Toast toast;
        if(mCheckResult.checkResult(mPuzzle)){
            toast =Toast.makeText(getActivity(),R.string.Complete_toast,Toast.LENGTH_SHORT);
            timer.stop();
            Intent intent = new Intent(getActivity(), GifActivity.class);
            startActivity(intent);
        }else{
            toast =Toast.makeText(getActivity(),R.string.Fail_toast,Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    /**
     * to change text for buttons when user switches language
     */
    public void changeButtonTextforSwitchLanguage(){
        for (int j = 0; j < mPUZZLETOTALSIZE; j++){
            TextView tobeChangedButton = layout.findViewById(j);
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

    /**
     * to generate grid layout
     */
    public void initializeGridLayout(GridLayout gridLayout){
        for (int i = 0; i < mPUZZLETOTALSIZE; i++) {
            final TextView textView = new TextView(getActivity());
            int x = i % mPUZZLESIZE;
            int y = i / mPUZZLESIZE;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.rowSpec = GridLayout.spec(y, 1f);
            params.columnSpec = GridLayout.spec(x, 1f);

            //TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            textView.setTextSize(calFontSize());
            textView.setTextColor(Color.parseColor("#282626"));
            // set button style
            if (mPuzzle[y][x].getFlag() != -1 ) {
                textView.setBackground(getResources().getDrawable(R.drawable.emptybutton));
            }else{
                textView.setBackground(getResources().getDrawable(R.drawable.presetbutton));
            }

            textView.setId(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonId = textView.getId();
                    int x = buttonId % mPUZZLESIZE;
                    int y = buttonId / mPUZZLESIZE;
                    //change highlighted buttons back to origin color
                    if(highlightedButton != -1)
                        changeHighlightBack(highlightedButton);
                    if(listeningModeFlag == -1 ){
                        //in normal mode
                        //when pressing preset grid, give hint; otherwise show dialog
                        if(mPuzzle[y][x].getFlag() == -1 ){
                            if(mPuzzle[y][x].getFlag() == -1){
                                String str;
                                if (switchLanguageFlag == 1 )
                                    str = mPuzzle[y][x].getLanguageTwo();
                                else
                                    str = mPuzzle[y][x].getLanguageOne();
                                Toast toast = Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                                familiarity[mPuzzle[y][x].getNumber()-1] += 1;//for familiarity
                            }
                        }else{
                            erasedButtonId= buttonId;
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
                                textToSpeech.setPitch(0.9f);
                                //set to default
                                textToSpeech.setSpeechRate(0.9f);
                                String utteranceId=this.hashCode() + "";
                                textToSpeech.speak(mPuzzle[y][x].getLanguageTwo(), TextToSpeech.QUEUE_FLUSH, null,utteranceId);
                            }
                        }else{
                            erasedButtonId= buttonId;
                            showRadioDialog(buttonId);
                            //highlight corresponding row and column
                            highlightButton(buttonId);
                            highlightedButton = buttonId;
                        }
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int buttonId = textView.getId();
                    int x = buttonId % mPUZZLESIZE;
                    int y = buttonId / mPUZZLESIZE;
                    Toast toast;
                    String containedStr; //content of cell
                    if(switchLanguageFlag == 1 )
                        containedStr = mPuzzle[y][x].getLanguageOne();
                    else
                        containedStr = mPuzzle[y][x].getLanguageTwo();
                    toast = Toast.makeText(getActivity(), containedStr, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return true;
                }
            });

            textView.setGravity(Gravity.CENTER);
            params.setMargins(2, 2, 2, 2);
            gridLayout.addView(textView, params);
        }
    }

    /**
     * to control listening mode
     */
    public void listeningModeControl(){
        if(listeningModeFlag == 1){
            switchButton.setEnabled(false);
            switchButton.setBackground(getResources().getDrawable(R.drawable.disable_button));
            changeButtobTextsforListening();
        }else {
            switchButton.setEnabled(true);
            switchButton.setBackground(getResources().getDrawable(R.drawable.buttons));
            changeButtonTextforSwitchLanguage();
        }
    }

    /**
     * calculate font size
     */
    public int calFontSize(){
        int fontSize = mFONTSIZE;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        float xdpi = displaymetrics.xdpi;
        float ydpi = displaymetrics.ydpi;
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        if(width > height)
            fontSize += 2;
        float width2 = (width / xdpi)*(width / xdpi);
        float height2 = (height / ydpi)*(width / xdpi);
        float size =  (float) Math.sqrt(width2+height2);

        if (size <= 6) {// the real screen size in inches
            return fontSize;
        }else if (size <= 7) {
            return fontSize+1;
        }else if (width <= 8){
            return fontSize+2;
        }else if (width <= 9) {
            return fontSize + 5;
        }else {
            return fontSize+7;
        }
    }

    /**
     * initialize TextToSpeech
     * @param status: required
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity(), R.string.Fail_sound_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDetach(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDetach();
    }

}
