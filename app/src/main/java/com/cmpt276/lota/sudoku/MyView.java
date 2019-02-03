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
    private int mButtonPosition=11;

    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mGridWidth;
    private Paint mPresetGridPaint;
    private Paint mBlankGridPaint;
    private Paint mGridLinePaint;
    private Paint mGridTextPaint;
    private Paint mTypeInButtonColorPaint;
    private Paint mTypeInButtonStrokePaint;

    private int mGridLeftBlank =20;//to set for a 20(integer) space between grids and screen edge

    private int mPreviousX=-99;
    private int mPreviousY=-99;

    float offX;
    float offY;

    ArrayList<Language> lan= new ArrayList<Language>();
    private Language mPuzzle[][];

    private CheckResult mCheckResult;
    private int SwitchLaguageFlag=1;//1 is 1st lan, -1 is second lan

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
    }

    public void onDraw(Canvas canvas){

        // Set background
        canvas.drawColor(getResources().getColor(R.color.mBackgroundPaint_Color));

        //Draw grids
        //to draw grids background
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

        //Draw preset grids text
        Paint.FontMetrics fontMetrics = mGridTextPaint.getFontMetrics();
        offX = mGridWidth / 2;//center point
        offY = mGridWidth / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;//baseline for text

        for (int j = 0; j < mPuzzleSize; j++) {
            for (int i = 0; i < mPuzzleSize; i++) {
                if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==-1) {
                    if(SwitchLaguageFlag == 1)
                        canvas.drawText(mPuzzle[j][i].getLanguageOne(), i * mGridWidth + mGridLeftBlank + offX, (j + 1) * mGridWidth + mGridLeftBlank + offY, mGridTextPaint);
                    else
                        canvas.drawText(mPuzzle[j][i].getLanguageTwo(), i * mGridWidth + mGridLeftBlank + offX, (j + 1) * mGridWidth + mGridLeftBlank + offY, mGridTextPaint);
                }else if( mPuzzle[j][i].getNumber() !=0 && mPuzzle[j][i].getFlag()==1) {
                    if(SwitchLaguageFlag == 1)
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
            if(SwitchLaguageFlag == 1)
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

        //check if touched preset grids
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

        //when pressed check answer button
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
            SwitchLaguageFlag *= -1;
            invalidate();
            return false;
        }

        if( x< mGridLeftBlank || x> 9*mGridWidth+ mGridLeftBlank || y> (mButtonPosition+1)*mGridWidth+ mGridLeftBlank || (y > 10*mGridWidth+ mGridLeftBlank && y < mButtonPosition*mGridWidth+ mGridLeftBlank) || y < mGridWidth+ mGridLeftBlank) {
            //do nothing, out of clickable scale
            Toast toast = Toast.makeText(this.mContext, "dont", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return false;
        }else if( mPreviousX != -99 && mPreviousY != -99 ){

            if((x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank) && (y >= mButtonPosition*mGridWidth+ mGridLeftBlank && y<= (mButtonPosition+1)*mGridWidth+ mGridLeftBlank)) {
                //press grid first
                if((x <= (mPreviousX*mGridWidth+ mGridLeftBlank) && mPreviousX >= mGridLeftBlank) && (mPreviousY >= mButtonPosition*mGridWidth+ mGridLeftBlank && mPreviousY<= (mButtonPosition+1)*mGridWidth+ mGridLeftBlank))
                    return false;
                puzzleYIndex = (mPreviousY - (mPreviousY - mGridLeftBlank) % mGridLeftBlank) / mGridWidth - 1;
                puzzleXIndex = (mPreviousX - mGridLeftBlank) / mGridWidth;
                lanIndex = (x - mGridLeftBlank) / mGridWidth + 1;

            }else if( (x <= (9*mGridWidth+ mGridLeftBlank) && x >= mGridLeftBlank)&& ( y >= mGridWidth+ mGridLeftBlank && y <= 10*mGridWidth+ mGridLeftBlank)) {
                //press button first
                if( (mPreviousX <= (9*mGridWidth+ mGridLeftBlank) && mPreviousX >= mGridLeftBlank)&& ( mPreviousY >= mGridWidth+ mGridLeftBlank && mPreviousY <= 10*mGridWidth+ mGridLeftBlank))
                    return false;
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


        //bug 不能重点两次 button fixed
        //bug 点出界 fixed
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