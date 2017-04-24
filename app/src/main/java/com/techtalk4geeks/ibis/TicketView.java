package com.techtalk4geeks.ibis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by alex on 4/22/17.
 */

public class TicketView extends View {
    private Paint mPaint;
    private String mText;

    private int parentHeight;
    private int parentWidth;

    private int color = Color.parseColor("#FBC69A"); // Default color

    public TicketView(Context context) {
        super(context);
        initTicketView();
    }

    public TicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTicketView();

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TicketView);

        CharSequence s = a.getString(R.styleable.TicketView_android_text);
        if (s != null) {
            setText(s.toString());
        }

        setTextColor(a.getColor(R.styleable.TicketView_android_textColor, 0xFF000000));

        int textSize = a.getDimensionPixelOffset(R.styleable.TicketView_android_textSize, 0);
        if (textSize > 0) {
            setTextSize(textSize);
        }

        a.recycle();
    }

    public TicketView(Context context, int color) {
        super(context);
        initTicketView();

        this.color = color;

        // TODO: Complete constructor
    }

    private void initTicketView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        // Must manually scale the desired text size to match screen density
        mPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mPaint.setColor(0xFF000000);
//        setPadding(3, 3, 3, 3);
    }

    public void setText(String text) {
        mText = text;
        requestLayout();
        invalidate();
    }

    public void setTextSize(int size) {
        mPaint.setTextSize(size);
        requestLayout();
        invalidate();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(widthMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) mPaint.measureText(mText) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        parentWidth = MeasureSpec.getSize(measureSpec) - getPaddingRight();
        parentHeight = parentWidth / 4;
        return parentHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float textHeight = mPaint.ascent();
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), parentWidth, parentHeight, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(mText, getPaddingLeft(), (parentHeight - textHeight) / 2, mPaint);
        try {
            mPaint.setColor(Color.parseColor(mText)); // Box color
        } catch (IllegalArgumentException e) {
            Log.e("Ibis", "Color could not be parsed!");
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.error);
            canvas.drawBitmap(b, parentWidth * 0.75f, 0, mPaint); // TODO: Check if bitmap drawing works
        }
        canvas.drawRect(parentWidth * 0.75f, 0, parentWidth, parentHeight, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(parentWidth * 0.75f, 0, parentWidth * 0.75f, parentHeight, mPaint);
        canvas.drawLine(getPaddingLeft(), 0, parentWidth, 0, mPaint);
        canvas.drawLine(getPaddingLeft(), parentHeight, parentWidth, parentHeight, mPaint);
        canvas.drawLine(getPaddingLeft(), parentHeight, getPaddingLeft(), 0, mPaint);
        canvas.drawLine(parentWidth, parentHeight, parentWidth, 0, mPaint);
    }
    // TODO: Work on adjusting padding
}