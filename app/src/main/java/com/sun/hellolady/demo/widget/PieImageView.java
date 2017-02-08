package com.sun.hellolady.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 项目名称：hellolady
 * 类描述：显示派(Pie)形的进度条
 * 创建人：Jiamin.Sun
 * 创建时间：5/31/2016 10:03 AM
 */
public class PieImageView  extends ImageView {

    private final int MAX_PROGRESS = 100;
    private Paint mArcPaint;
    private RectF mBound;
    private Paint mCirclePaint;
    private int mProgress = 0;

    public PieImageView(Context context) {
        this(context, null, 0);
    }

    public PieImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setProgress(@IntRange(from = 0, to = MAX_PROGRESS) int mProgress) {
        this.mProgress = mProgress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void init() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mArcPaint.setStrokeWidth(dipToPx(0.1f));
        mArcPaint.setColor(Color.argb(120, 0xff, 0xff, 0xff));

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(dipToPx(2));
        mCirclePaint.setColor(Color.argb(120, 0xff, 0xff, 0xff));
        mBound = new RectF();
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        int max = w + h - min;
        int r = min / 5;
        //set up a square in the imageView
        mBound.set(max / 2 - r, min / 2 - r, max / 2 + r, min / 2 + r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mProgress != MAX_PROGRESS && mProgress != 0) {
            float mAngle = mProgress * 360f / MAX_PROGRESS;
            canvas.drawArc(mBound, 270, mAngle, true, mArcPaint);
            canvas.drawCircle(mBound.centerX(), mBound.centerY(), mBound.height() / 2, mCirclePaint);
        }
    }


    private float dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return  (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
