package com.jyd.defaultdemos.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jyd.defaultdemos.util.Tool;


/**
 * Created by dongxiaoliu on 16/7/18.
 * * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */
public class PercentCircleView extends View {

    private Paint mCirclePaint, mArcPaint, mTextPaint;
    private int mScreenWith, mCircleXY;
    private Float mRadius;
    private RectF mArcRectF;
    private float mSweepValue = 0;
    private String mShowText;

    public PercentCircleView(Context context) {
        super(context);
        configCanvas();
    }

    public PercentCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configCanvas();
    }

    public PercentCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        configCanvas();
    }

    public void configCanvas() {
        mCirclePaint = new Paint();
        mArcPaint = new Paint();
        mTextPaint = new Paint();
        mCirclePaint.setColor(Color.GREEN);
        mArcPaint.setColor(Color.GREEN);
        mArcPaint.setColor(Color.GREEN);
        mScreenWith = 1080;
        mCircleXY = mScreenWith / 2;
        mRadius = (float) (mScreenWith * 0.5 / 2);
        mArcRectF = new RectF(
                (float)(mScreenWith * 0.1),
                (float)(mScreenWith * 0.1),
                (float)(mScreenWith * 0.9),
                (float)(mScreenWith * 0.9));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        canvas.drawArc(mArcRectF, 270, mSweepValue, false, mArcPaint);
//        mCanvas.drawText(mShowText, 0, mShowText.length(), mCircleXY, mCircleXY + （ / 4), mTextPaint);
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            mSweepValue = 25;
        }
        this.invalidate();
    }
}
