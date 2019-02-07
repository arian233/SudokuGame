package com.cmpt276.lota.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class MyView extends View {

    private final int mPuzzleSize =9;
    private final int mButtonPosition=11;

    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;// it's useful in the later iteration
    private int mGridWidth;
    private final int mGridLeftBlank =20;//to set for a space between grids and screen edge

    private Paint mPresetGridPaint;
    private Paint mBlankGridPaint;
    private Paint mGridLinePaint;
    private Paint mGridTextPaint;
    private Paint mTypeInButtonColorPaint;
    private Paint mTypeInButtonStrokePaint;

    private int mPreviousX=-99;
    private int mPreviousY=-99;

    float offX;//for centralize the text in each grid
    float offY;

    ArrayList<Language> lan= new ArrayList<Language>();
    private Language mPuzzle[][];

    private CheckResult mCheckResult;
    private int SwitchLanguageFlag=1;//1 is 1st lan, -1 is second lan

    public MyView(Context context){
        super(context);
        this.mContext = context;
        init();
    }

    public MyView(Context context,AttributeSet attrs){
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    /**
     * to initialize paint, size, and puzzle
     */
    public void init(){
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mGridWidth = (mScreenWidth - 40) / 9;

        PuzzleGenerator generator= new PuzzleGenerator();//initialize puzzleGenerator
        lan = generator.generateLanguage(lan);//initialize Language
        mPuzzle = generator.generatePuzzle();

        mPresetGridPaint = new Paint();//set for preset grids
        mPresetGridPaint.setStyle(Paint.Style.FILL);
        mPresetGridPaint.setColor(getResources().getColor(R.color.presetGridPaint_Color));

        mBlankGridPaint = new Paint();//set for blank grids
        mBlankGridPaint.setStyle(Paint.Style.FILL);
        mBlankGridPaint.setColor(getResources().getColor(R.color.blankGridPaint_Color));

        mGridLinePaint = new Paint();//set for grids lines
        mGridLinePaint.setStrokeWidth(2);
        mGridLinePaint.setColor(getResources().getColor(R.color.gridLinePaint_Color));

        mGridTextPaint = new Paint();//set for Texts
        mGridTextPaint.setColor(getResources().getColor(R.color.gridTextPaint_Color));
        mGridTextPaint.setTextSize(mGridWidth*0.25f);
        mGridTextPaint.setTextAlign(Paint.Align.CENTER);
        mGridTextPaint.setAntiAlias(true);

        mTypeInButtonColorPaint = new Paint();//set for typeIn Button
        mTypeInButtonColorPaint.setStyle(Paint.Style.FILL);
        mTypeInButtonColorPaint.setColor(getResources().getColor(R.color.typeInButtonPaint_Color));

        mTypeInButtonStrokePaint = new Paint();
        mTypeInButtonStrokePaint.setStyle(Paint.Style.STROKE);
        mTypeInButtonStrokePaint.setColor(getResources().getColor(R.color.gridLinePaint_Color));
        mTypeInButtonStrokePaint.setStrokeWidth(7);

        mCheckResult = new CheckResult();

        //not yet finished, this is if user is long pressing the preset grid, it will popup a translation of that grid
//        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public void onLongPress(MotionEvent e) {
//                int x = (int)e.getX();
//                int y = (int)e.getY();
//                int puzzleYIndex=0, puzzleXIndex=0;
//                if( (x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank)&& ( y >= mGridWidth+ mGridLeftBlank && y <= 10*mGridWidth+ mGridLeftBlank)) {
//                    puzzleYIndex = (y - (y - mGridLeftBlank) % mGridLeftBlank) / mGridWidth - 1;
//                    puzzleXIndex = (x - mGridLeftBlank) / mGridWidth;
//                    if (mPuzzle[puzzleYIndex][puzzleXIndex].getNumber() ==0){
//                        Toast toast =Toast.makeText(mContext,R.string.FailLongPress_toast,Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
//                        toast.show();
//                    }else{
//                        String str;
//                        if(SwitchLanguageFlag == 1){
//                            str = mPuzzle[puzzleYIndex][puzzleXIndex].getLanguageOne();
//                        }else{
//                            str = mPuzzle[puzzleYIndex][puzzleXIndex].getLanguageTwo();
//                        }
//                        Toast toast =Toast.makeText(mContext,str,Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
//                        toast.show();
//                    }
//                }
//            }
//        };
    }

    /**
     * to draw the text, grids, buttons
     */
    @Override
    public void onDraw(Canvas canvas){

        // Set background
        canvas.drawColor(getResources().getColor(R.color.mBackgroundPaint_Color));

        //Draw grids
        //to draw grids background
        canvas.drawRect(mGridLeftBlank, mGridWidth+ mGridLeftBlank, 9*mGridWidth+ mGridLeftBlank, (10)*mGridWidth+ mGridLeftBlank , mBlankGridPaint);

        //did't delete the code b/c need this as a record to write other functions related to x y positions
//        for (int j = 0; j < 9; j++) {
//            for (int i = 0; i < 9; i++) {
//                canvas.drawRect(i*mGridWidth+ mGridLeftBlank, (j+1)*mGridWidth+ mGridLeftBlank, i*mGridWidth+ mGridLeftBlank +mGridWidth, (j+1)*mGridWidth+ mGridLeftBlank +mGridWidth, mBlankGridPaint);
//            }
//        }

        //draw preset grids
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==-1)
                    canvas.drawRect(i*mGridWidth+ mGridLeftBlank, (j+1)*mGridWidth+ mGridLeftBlank, i*mGridWidth+ mGridLeftBlank +mGridWidth, (j+1)*mGridWidth+ mGridLeftBlank +mGridWidth, mPresetGridPaint);
            }
        }

        //Draw grids lines(thin)
        //row
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(mGridLeftBlank,(i+1)*mGridWidth+ mGridLeftBlank,9*mGridWidth+ mGridLeftBlank,(i+1)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }
        //column
        for (int i = 0; i < 10; i++) {
            canvas.drawLine((i)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank,(i)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank +9*mGridWidth, mGridLinePaint);
        }

        //Draw grids lines(thick)
        mGridLinePaint.setStrokeWidth(7);
        //row
        for (int i = 1; i < 3; i++) {
            canvas.drawLine(mGridLeftBlank,(i*3+1)*mGridWidth+ mGridLeftBlank,9*mGridWidth+ mGridLeftBlank,(i*3+1)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }
        //column
        for (int i = 1; i < 3; i++) {
            canvas.drawLine((i*3)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank,(i*3)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank +9*mGridWidth, mGridLinePaint);
        }

        //Draw preset grids text
        Paint.FontMetrics fontMetrics = mGridTextPaint.getFontMetrics();
        offX = mGridWidth / 2;//center point
        offY = mGridWidth / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;//baseline for text

        for (int j = 0; j < mPuzzleSize; j++) {
            for (int i = 0; i < mPuzzleSize; i++) {
                if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==-1) {
                    if(SwitchLanguageFlag == 1)
                        canvas.drawText(mPuzzle[j][i].getLanguageOne(), i * mGridWidth + mGridLeftBlank + offX, (j + 1) * mGridWidth + mGridLeftBlank + offY, mGridTextPaint);
                    else
                        canvas.drawText(mPuzzle[j][i].getLanguageTwo(), i * mGridWidth + mGridLeftBlank + offX, (j + 1) * mGridWidth + mGridLeftBlank + offY, mGridTextPaint);
                }else if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==1) {
                    if(SwitchLanguageFlag == 1)
                        canvas.drawText(mPuzzle[j][i].getLanguageTwo(), i * mGridWidth + mGridLeftBlank + offX, (j + 1) * mGridWidth + mGridLeftBlank + offY, mGridTextPaint);
                    else
                        canvas.drawText(mPuzzle[j][i].getLanguageOne(), i * mGridWidth + mGridLeftBlank + offX, (j + 1) * mGridWidth + mGridLeftBlank + offY, mGridTextPaint);
                }
            }
        }

        //Draw typeIn Button
        for (int i = 0; i < 9; i++) {
            canvas.drawRect(i*mGridWidth+ mGridLeftBlank, (mButtonPosition)*mGridWidth+ mGridLeftBlank, i*mGridWidth+ mGridLeftBlank +mGridWidth, (mButtonPosition)*mGridWidth+ mGridLeftBlank +mGridWidth, mTypeInButtonColorPaint);
        }

        //Draw lines for typeIn buttons
        for (int i = 0; i < 2; i++) {
            canvas.drawLine(mGridLeftBlank,(mButtonPosition+i)*mGridWidth+ mGridLeftBlank,9*mGridWidth+ mGridLeftBlank,(mButtonPosition+i)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(i*mGridWidth+ mGridLeftBlank,(mButtonPosition)*mGridWidth+ mGridLeftBlank,i*mGridWidth+ mGridLeftBlank,(mButtonPosition+1)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }

        //Draw Texts for typeIn buttons
        for (int i = 0; i < 9; i++) {
            if(SwitchLanguageFlag == 1)
                canvas.drawText(lan.get(i+1).getLanguageTwo(), i*mGridWidth+ mGridLeftBlank +offX, (mButtonPosition)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);
            else
                canvas.drawText(lan.get(i+1).getLanguageOne(), i*mGridWidth+ mGridLeftBlank +offX, (mButtonPosition)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);
        }

        //Draw Check Answer Button
        offX = mGridWidth;
        canvas.drawRect(1*mGridWidth+ mGridLeftBlank, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank, 2*mGridWidth+ mGridLeftBlank +mGridWidth, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +mGridWidth, mTypeInButtonColorPaint);
        canvas.drawRect(1*mGridWidth+ mGridLeftBlank, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank, 2*mGridWidth+ mGridLeftBlank +mGridWidth, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +mGridWidth, mTypeInButtonStrokePaint);
        canvas.drawText("Check Answer", 1*mGridWidth+ mGridLeftBlank +offX, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);

        //Draw switch language Button
        canvas.drawRect(4*mGridWidth+ mGridLeftBlank, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank, 5*mGridWidth+ mGridLeftBlank +mGridWidth, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +mGridWidth, mTypeInButtonColorPaint);
        canvas.drawRect(4*mGridWidth+ mGridLeftBlank, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank, 5*mGridWidth+ mGridLeftBlank +mGridWidth, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +mGridWidth, mTypeInButtonStrokePaint);
        canvas.drawText("Switch Language", 4*mGridWidth+ mGridLeftBlank +offX, (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);

        //set width back, for reDraw
        mGridLinePaint.setStrokeWidth(2);
        //Log.v("onDraw(Canvas canvas)","" + this.getHeight()+ "   " + this.getWidth());
    }

    /**
     * to detect touch event
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int x = (int)e.getX();
        int y = (int)e.getY();
        int puzzleYIndex=0, puzzleXIndex=0, lanIndex=0;
        int copyFlag=0;//0 means detect a valid touch; 1 means found a invalid touching position and clear previous touching position( also means a pair of touching completed)

        //check if touched preset grids, it will popup a toast that tells the hint(translation of that grid).
        if( (x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank)&& (y <= 10*mGridWidth+ mGridLeftBlank && y >= mGridWidth+ mGridLeftBlank)){
            puzzleYIndex = (y-(y - mGridLeftBlank) % mGridLeftBlank) / mGridWidth -1;
            puzzleXIndex = (x - mGridLeftBlank) / mGridWidth ;
            if(puzzleYIndex == 9)
                puzzleYIndex = 8;//in case the very bottom of row 9 being touched and cause crash
            if(mPuzzle[puzzleYIndex][puzzleXIndex].getFlag() == -1){
                String str;
                if (SwitchLanguageFlag ==1 )
                    str = mPuzzle[puzzleYIndex][puzzleXIndex].getLanguageTwo();
                else
                    str = mPuzzle[puzzleYIndex][puzzleXIndex].getLanguageOne();

                Toast toast = Toast.makeText(this.mContext,str,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return false;
            }
        }

        //when pressing check answer button, it call a function to check if there's any empty set.
        //here don't check repetition b/c we check it every time user input a value
        //here don't check with answer because we can't guarantee there's exactly only one answer.
        if((x <= 2*mGridWidth+ mGridLeftBlank +mGridWidth && x >= 1*mGridWidth+ mGridLeftBlank) && (y >= (mButtonPosition+2)*mGridWidth+ mGridLeftBlank && y<= (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +mGridWidth)){
            if(mCheckResult.checkResult(mPuzzle)){
                Toast toast =Toast.makeText(this.mContext,R.string.Complete_toast,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return false;
            }else{
                Toast toast =Toast.makeText(this.mContext,R.string.Fail_toast,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return false;
            }
        }

        //when pressed Switch language button
        if((x <= 5*mGridWidth+ mGridLeftBlank +mGridWidth && x >= 4*mGridWidth+ mGridLeftBlank) && (y >= (mButtonPosition+2)*mGridWidth+ mGridLeftBlank && y<= (mButtonPosition+2)*mGridWidth+ mGridLeftBlank +mGridWidth)){
            SwitchLanguageFlag *= -1;
            invalidate();
            return false;
        }

        //do nothing but show a caution toast, b/c user is pressing outside of the grids and buttons; the caution toast will be removed in the later iteration, b/c it's annoying.
        if( x< mGridLeftBlank || x> 9*mGridWidth+ mGridLeftBlank || y> (mButtonPosition+1)*mGridWidth+ mGridLeftBlank || (y > 10*mGridWidth+ mGridLeftBlank && y < mButtonPosition*mGridWidth+ mGridLeftBlank) || y < mGridWidth+ mGridLeftBlank) {
            Toast toast = Toast.makeText(this.mContext, "you're pressing the empty space", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return false;

        }else if( mPreviousX != -99 && mPreviousY != -99 ){//start from here is check either user press empty grid first, or buttons first, and do reactions accordingly.

            //press grid first， now is button
            if((x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank) && (y >= mButtonPosition*mGridWidth+ mGridLeftBlank && y<= (mButtonPosition+1)*mGridWidth+ mGridLeftBlank)) {

                if((x <= (mPreviousX*mGridWidth+ mGridLeftBlank) && mPreviousX >= mGridLeftBlank) && (mPreviousY >= mButtonPosition*mGridWidth+ mGridLeftBlank && mPreviousY<= (mButtonPosition+1)*mGridWidth+ mGridLeftBlank))
                    return false;
                puzzleYIndex = (mPreviousY - (mPreviousY - mGridLeftBlank) % mGridLeftBlank) / mGridWidth - 1;
                puzzleXIndex = (mPreviousX - mGridLeftBlank) / mGridWidth;
                lanIndex = (x - mGridLeftBlank) / mGridWidth + 1;

            }else if( (x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank)&& ( y >= mGridWidth+ mGridLeftBlank && y <= 10*mGridWidth+ mGridLeftBlank)) { //press button before, now is grid

                if( (mPreviousX <= (9*mGridWidth+ mGridLeftBlank) && mPreviousX >= mGridLeftBlank)&& ( mPreviousY >= mGridWidth+ mGridLeftBlank && mPreviousY <= 10*mGridWidth+ mGridLeftBlank))
                    return false;
                puzzleYIndex = (y - (y - mGridLeftBlank) % mGridLeftBlank) / mGridWidth - 1;
                puzzleXIndex = (x - mGridLeftBlank) / mGridWidth;
                lanIndex = (mPreviousX - mGridLeftBlank) / mGridWidth + 1;
            }

            if(puzzleYIndex == 9)
                puzzleYIndex = 8;

            //check repetition
            mPuzzle[puzzleYIndex][puzzleXIndex] = new Language(lan.get(lanIndex).getNumber(), lan.get(lanIndex).getLanguageOne(), lan.get(lanIndex).getLanguageTwo(),1);
            if (!mCheckResult.checkValid(mPuzzle,puzzleYIndex,puzzleXIndex)){

                //invalid input, found repetition
                mPuzzle[puzzleYIndex][puzzleXIndex] = new Language(lan.get(0).getNumber(), lan.get(0).getLanguageOne(), lan.get(0).getLanguageTwo(),0);
                Toast toast =Toast.makeText(this.mContext,R.string.FoundRepeat_toast,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            copyFlag=1;
        }

        invalidate();
        if(copyFlag==0){
            mPreviousX = x;
            mPreviousY = y;
        }else{
            mPreviousX = -99;
            mPreviousY = -99;
        }
        return false;
    }


}
