package com.xieyaxin.space.forme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by John on 2017/4/18.
 */

public class OpenView extends View {

    private String topText;
    private int topTextColor;
    private int topTextSize;
    private int layerColor;
    private float layerAlpha;
    private int floorView;

    private Paint paint_topText;
    private Paint paint_layer;
    private Rect mBounds;

    public OpenView(Context context) {
        super(context);
        init(context,null,0);
    }

    public OpenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public OpenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public OpenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context,AttributeSet attrs,int defStyleAttr){
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.OpenView,defStyleAttr,0);
        int n=a.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.OpenView_topText:
                    topText=a.getString(attr);
                    break;
                case R.styleable.OpenView_topTextColor:
                    topTextColor=a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.OpenView_topTextSize:
                    //默认16sp
                    topTextSize=a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.OpenView_layerColor:
                    layerColor=a.getColor(attr,Color.YELLOW);

                    break;
                case R.styleable.OpenView_layerAlpha:

                    layerAlpha=a.getFloat(attr,0.7f);
                    break;
                case R.styleable.OpenView_floorView:
                    floorView=a.getResourceId(attr,R.mipmap.ic_launcher);
                    break;
            }
        }
        a.recycle();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topText=randomText();

                postInvalidate();
            }
        });
    }

    private String randomText(){
        Random random=new Random();
        Set<Integer> set=new HashSet<Integer>();
        while (set.size()<4){
            int randomInt=random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb=new StringBuffer();
        for (Integer i:set){
            
            sb.append(""+i);
            
        }
        return sb.toString();
    }

    private void initTextPaint(){
        paint_topText=new Paint();
        paint_topText.setColor(topTextColor);
        paint_topText.setTextSize(topTextSize);
        mBounds=new Rect();
        paint_topText.getTextBounds(topText,0,topText.length(),mBounds);
    }

    private void initLayerPaint(){
        paint_layer=new Paint();
        paint_layer.setColor(layerColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
//        int width;
//        int height;
//        if (widthMode==MeasureSpec.EXACTLY){
//            width=widthSize;
//        }else {
//// TODO: 2017/4/19 asdasdasdasdsad 
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        initTextPaint();
        initLayerPaint();
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint_layer);
        canvas.drawText(topText,getWidth()/2-mBounds.width()/2,getHeight()/2+mBounds.height()/2,paint_topText);
    }

}
