package com.cmpt276.lota.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;



public class MyView extends View {

    private float width;
    private float height;

    public MyView(Context context){
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        this.width=w/9f;
        this.height=h/9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }



    public void onDraw(Canvas canvas){
//        Paint p =new Paint();
//        p.setTextSize(20);
//        p.setColor(Color.BLUE);
//        c.drawText("Hello", 20, 30, p);
///////
        Paint background_paint = new Paint();
        background_paint.setColor(Color.BLUE);
        //canvas.drawText("Hello", 20, 30, background_paint);
        canvas.drawRect(0, 0, getWidth(), getHeight(), background_paint);

        Paint white=new Paint();
        white.setColor(Color.WHITE);

        Paint light=new Paint();
        light.setColor(Color.LTGRAY);

        Paint dark=new Paint();
        dark.setColor(Color.BLACK);

        for(int i=0;i<9;i++)
        {
            //画出横向的线
            canvas.drawLine(0, i*height, getHeight(), i*height, light);
            canvas.drawLine(0, i*height+1, getHeight(), i*height+1, white);
            //画出纵向的线
            canvas.drawLine( i*width,0, i*width,getHeight(), light);
            canvas.drawLine( i*width+1,0, i*width+1, getHeight(), white);
        }
        for(int i=0;i<9;i++)
        {
            if(i%3!=0)
            {
                continue;
            }
            canvas.drawLine(0, i*height, getHeight(), i*height, dark);
            canvas.drawLine(0, i*height+1, getHeight(), i*height+1, white);
            //画出纵向的线
            canvas.drawLine( i*width,0, i*width,getHeight(), dark);
            canvas.drawLine( i*width+1,0, i*width+1, getHeight(), white);
        }

//        Paint number_paint=new Paint();
//        number_paint.setColor(Color.BLACK);
//        //number_paint.setStyle(Paint.Style.STROKE);
//        number_paint.setTextSize(height*0.75f);
//        number_paint.setTextAlign(Paint.Align.CENTER);
//
//        FontMetrics fm=number_paint.getFontMetrics();
//        float x=width/2;
//        float y=height/2-(fm.ascent+fm.descent)/2;



//        for(int i=0;i<9;i++)
//        {
//            for(int j=0;j<9;j++)
//            {
//                canvas.drawText(game.getTileString(i, j), width*i+x, height*j+y, number_paint);
//            }
//        }


    }



}
