package com.cmpt276.lota.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MyView extends View {

    private int mPuzzleSize =9;

    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mGridWidth;
    private Paint mBackgroundPaint;
    private Paint mPresetGridPaint;
    private Paint mBlankGridPaint;
    private Paint mGridLinePaint;
    private Paint mGridTextPaint;
    private Paint mTypeInButtonPaint;

    private int mGridLeftBlank =20;//to set for a 20(integer) space between grids and screen edge

    private int mPreviousX=-99;
    private int mPreviousY=-99;

    float offX;
    float offY;

    ArrayList<Language> lan= new ArrayList<Language>();
    private Language mPuzzle[][];

    private CheckResult mCheckResult;

    public MyView(Context context){
        super(context);
        this.mContext = context;
        init();
    }

    public void init(){
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mGridWidth = (mScreenWidth - 40) / 9;

        PuzzleGenerator generator= new PuzzleGenerator();//initialize puzzleGenerator
        lan = generator.generateLanguage(lan);//initialize Language
        mPuzzle = generator.generatePuzzle();

        mBackgroundPaint = new Paint();//set for whole background
        mBackgroundPaint.setStyle(Paint.Style.FILL);// fill with color
        mBackgroundPaint.setColor(getResources().getColor(R.color.mBackgroundPaint_Color));// set Color

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
        mGridTextPaint.setTextSize(mGridWidth*0.3f);
        mGridTextPaint.setTextAlign(Paint.Align.CENTER);
        mGridTextPaint.setAntiAlias(true);

        mTypeInButtonPaint = new Paint();//set for typeIn Button
        mTypeInButtonPaint.setStyle(Paint.Style.FILL);
        mTypeInButtonPaint.setColor(getResources().getColor(R.color.typeInButtonPaint_Color));

        mCheckResult = new CheckResult();
    }

    public void onDraw(Canvas canvas){


        // Draw background
        canvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mBackgroundPaint);// draw rectangle as background

        //Draw grids
        //to draw grids backgroud
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                canvas.drawRect(i*mGridWidth+ mGridLeftBlank, (j+1)*mGridWidth+ mGridLeftBlank, i*mGridWidth+ mGridLeftBlank +mGridWidth, (j+1)*mGridWidth+ mGridLeftBlank +mGridWidth, mBlankGridPaint);
            }
        }

        //draw preset grids
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==-1)
                    canvas.drawRect(i*mGridWidth+ mGridLeftBlank, (j+1)*mGridWidth+ mGridLeftBlank, i*mGridWidth+ mGridLeftBlank +mGridWidth, (j+1)*mGridWidth+ mGridLeftBlank +mGridWidth, mPresetGridPaint);
            }
        }

        //Draw grids lines(thin)
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(mGridLeftBlank,(i+1)*mGridWidth+ mGridLeftBlank,9*mGridWidth+ mGridLeftBlank,(i+1)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }

        for (int i = 0; i < 10; i++) {
            canvas.drawLine((i)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank,(i)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank +9*mGridWidth, mGridLinePaint);
        }

        //Draw grids lines(thick)
        mGridLinePaint.setStrokeWidth(7);

        for (int i = 1; i < 3; i++) {
            canvas.drawLine(mGridLeftBlank,(i*3+1)*mGridWidth+ mGridLeftBlank,9*mGridWidth+ mGridLeftBlank,(i*3+1)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }

        for (int i = 1; i < 3; i++) {
            canvas.drawLine((i*3)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank,(i*3)*mGridWidth+ mGridLeftBlank,mGridWidth+ mGridLeftBlank +9*mGridWidth, mGridLinePaint);
        }


        Paint.FontMetrics fontMetrics = mGridTextPaint.getFontMetrics();
        offX = mGridWidth/2;
        offY = mGridWidth / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;

        //Draw preset grids text
        for (int j = 0; j < mPuzzleSize; j++) {
            for (int i = 0; i < mPuzzleSize; i++) {
                if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==-1)
                    canvas.drawText(mPuzzle[j][i].getLanguageOne(), i*mGridWidth+ mGridLeftBlank +offX, (j+1)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);
                else if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==1)
                    canvas.drawText(mPuzzle[j][i].getLanguageTwo(), i*mGridWidth+ mGridLeftBlank +offX, (j+1)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);
            }
        }

        //Draw typeIn Button
        for (int i = 0; i < 9; i++) {
            canvas.drawRect(i*mGridWidth+ mGridLeftBlank, (12)*mGridWidth+ mGridLeftBlank, i*mGridWidth+ mGridLeftBlank +mGridWidth, (12)*mGridWidth+ mGridLeftBlank +mGridWidth, mTypeInButtonPaint);
        }

        //Draw lines for buttons
        for (int i = 0; i < 2; i++) {
            canvas.drawLine(mGridLeftBlank,(12+i)*mGridWidth+ mGridLeftBlank,9*mGridWidth+ mGridLeftBlank,(12+i)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(i*mGridWidth+ mGridLeftBlank,(12)*mGridWidth+ mGridLeftBlank,i*mGridWidth+ mGridLeftBlank,(13)*mGridWidth+ mGridLeftBlank, mGridLinePaint);
        }

        //Draw Texts for buttons
        for (int i = 0; i < 9; i++) {
            canvas.drawText(lan.get(i+1).getLanguageTwo(), i*mGridWidth+ mGridLeftBlank +offX, (12)*mGridWidth+ mGridLeftBlank +offY, mGridTextPaint);
        }




        mGridLinePaint.setStrokeWidth(2);//set back, for reDraw
        //Log.v("onDraw(Canvas canvas)","" + this.getHeight()+ "   " + this.getWidth());

    }

    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        int x = (int)e.getX();
        int y = (int)e.getY();
        int puzzleYIndex=0, puzzleXIndex=0, lanIndex=0;
        int copyFlag=0;//0 is copy to previousXy, 1 means invalid touching position and clear previous input touching position(means a pair of touching completed)

        if( (x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank)&& (y <= 10*mGridWidth+ mGridLeftBlank && y >= mGridWidth+ mGridLeftBlank)){
            puzzleYIndex = (y-(y - mGridLeftBlank) % mGridLeftBlank) / mGridWidth -1;
            puzzleXIndex = (x - mGridLeftBlank) / mGridWidth ;
            if(mPuzzle[puzzleYIndex][puzzleXIndex].getFlag()==-1){
                Toast toast =Toast.makeText(this.mContext,R.string.DontTapRepeate_toast,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return false;
            }
        }

        if( mPreviousX != -99 && mPreviousY != -99 ){
            if( x< mGridLeftBlank || x> 9*mGridWidth+ mGridLeftBlank || y> 13*mGridWidth+ mGridLeftBlank || (y > 10*mGridWidth+ mGridLeftBlank && y < 12*mGridWidth+ mGridLeftBlank) || y < mGridWidth+ mGridLeftBlank){
                //do nothing, out of clickable scale
                Toast toast =Toast.makeText(this.mContext,"dont",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return false;

            }else if((x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank) && (y >= 12*mGridWidth+ mGridLeftBlank && y<= 13*mGridWidth+ mGridLeftBlank)) {
                //press grid first
                puzzleYIndex = (mPreviousY - (mPreviousY - mGridLeftBlank) % mGridLeftBlank) / mGridWidth - 1;
                puzzleXIndex = (mPreviousX - mGridLeftBlank) / mGridWidth;
                lanIndex = (x - mGridLeftBlank) / mGridWidth + 1;

            }else if( (x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank)&& ( y >= mGridWidth+ mGridLeftBlank && y <= 10*mGridWidth+ mGridLeftBlank)) {
                //press button first
                puzzleYIndex = (y - (y - mGridLeftBlank) % mGridLeftBlank) / mGridWidth - 1;
                puzzleXIndex = (x - mGridLeftBlank) / mGridWidth;
                lanIndex = (mPreviousX - mGridLeftBlank) / mGridWidth + 1;
            }

            //check repetition
            mPuzzle[puzzleYIndex][puzzleXIndex] = new Language(lan.get(lanIndex).getNumber(), lan.get(lanIndex).getLanguageOne(), lan.get(lanIndex).getLanguageTwo(),1);

            if (!mCheckResult.checkValid(mPuzzle,puzzleYIndex,puzzleXIndex)){
                mPuzzle[puzzleYIndex][puzzleXIndex] = new Language(lan.get(0).getNumber(), lan.get(0).getLanguageOne(), lan.get(0).getLanguageTwo(),0);//invalid input, found repetition
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
//Log.v("onTouch","xxxxxxxxxxxxxx:  " + puzzleYIndex+"   "+puzzleXIndex);