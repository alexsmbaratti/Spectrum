package com.techtalk4geeks.ibis;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by alex on 4/22/17.
 */

public class CollectionView extends View implements View.OnClickListener {
    Context context;
    private Paint mPaint;
    private String mText;
    private int parentHeight;
    private int parentWidth;
    private float textWidth;
    private float textHeight;

    public CollectionView(Context context) {
        super(context);
        this.context = context;
        initTicketView();
        setOnClickListener(this);
    }

    public CollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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

        setOnClickListener(this);

        a.recycle();
    }

    public CollectionView(Context context, String collectionName) {
        super(context);
        this.context = context;
        initTicketView();

        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());

        setTextSize((int) pixels);
        setText(collectionName);
        setOnClickListener(this);
        // TODO: Complete constructor
    }

    private void initTicketView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        // Must manually scale the desired text size to match screen density
        mPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mPaint.setColor(0xFF000000);

        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
//        setPadding(pixels, pixels, pixels, pixels);
    }

    public void setText(String text) {
        mText = text;
        requestLayout();
        invalidate();
    }

    public void setTextSize(int size) {
        mPaint.setTextSize(size);
        Log.d("Ibis", "Size is: " + size);
        requestLayout();
        invalidate();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(widthMeasureSpec));
    }

    public void onClick(View arg0) {
        Log.d("Ibis", "TicketView clicked");

        Intent colorIntent = new Intent(context, ColorDetailActivity.class);
        context.startActivity(colorIntent);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // TODO: Center text in ticket

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
        parentHeight = parentWidth / 3;
        return parentHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        parentHeight += getPaddingTop();

        mPaint.setColor(Color.WHITE);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), parentWidth, parentHeight, mPaint);
        mPaint.setColor(Color.BLACK);

        textWidth = (int) mPaint.measureText(mText);
        textHeight = mPaint.ascent();
        final float labelHeight = 2.2f / 3;

        canvas.drawText(mText, (parentWidth - textWidth) / 2, (parentHeight * labelHeight - textHeight) / 2, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), parentWidth, getPaddingTop(), mPaint);
        canvas.drawLine(getPaddingLeft(), parentHeight, parentWidth, parentHeight, mPaint);
        canvas.drawLine(getPaddingLeft(), parentHeight, getPaddingLeft(), getPaddingTop(), mPaint);
        canvas.drawLine(parentWidth, parentHeight, parentWidth, getPaddingTop(), mPaint);
        canvas.drawLine(getPaddingLeft(), parentHeight * labelHeight, parentWidth, parentHeight * labelHeight, mPaint);

        for (int i = 1; i < 10; i++) {
            canvas.drawLine(parentWidth * 0.1f * i, parentHeight * labelHeight, parentWidth * 0.1f * i, parentHeight, mPaint);
        }

        parentHeight -= getPaddingTop();
    }
    // TODO: Work on adjusting padding

}