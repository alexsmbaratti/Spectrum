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
import android.util.TypedValue;
import android.view.View;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by alex on 4/22/17.
 */

public class CMYKView extends View implements View.OnClickListener {
    public static final int TEXT_SIZE = 24;
    public static int DROP_HEIGHT = 96;
    String colorString;
    Context context;
    float C;
    float M;
    float Y;
    float K;
    float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE, getResources().getDisplayMetrics());
    private Paint mPaint;
    private String mText = "CMYK Percentages";
    private int parentHeight;
    private int parentWidth;
    private int color; // Default color
    private float textWidth;
    private float textHeight;
    private String colorName = "";

    public CMYKView(Context context) {
        super(context);
        this.context = context;
        initTicketView();
        setOnClickListener(this);
    }

    public CMYKView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initTicketView();

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TicketView);

        CharSequence s = a.getString(R.styleable.TicketView_android_text);

        setTextColor(a.getColor(R.styleable.TicketView_android_textColor, 0xFF000000));

        int textSize = a.getDimensionPixelOffset(R.styleable.TicketView_android_textSize, 0);
        if (textSize > 0) {
            setTextSize(textSize);
        }

        setOnClickListener(this);

        a.recycle();
    }

    public CMYKView(Context context, String colorString) {
        super(context);
        this.context = context;
        initTicketView();

        setTextSize((int) pixels);
        setOnClickListener(this);
        // TODO: Complete constructor
    }

    public CMYKView(Context context, float C, float M, float Y, float K) {
        super(context);
        this.context = context;
        initTicketView();

        this.C = C;
        this.M = M;
        this.Y = Y;
        this.K = K;

        Log.d("Ibis", "C = " + C);
        Log.d("Ibis", "M = " + M);
        Log.d("Ibis", "Y = " + Y);
        Log.d("Ibis", "K = " + K);

        setTextSize((int) pixels);
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
        setPadding(0, pixels, 0, pixels);
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

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(widthMeasureSpec));
    }

    public void onClick(View arg0) {
        Log.d("Ibis", "TicketView clicked");

        // TODO: onClick
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
        parentHeight = (int) (getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT * 4);
        return parentHeight;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        // Rectangle
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), parentWidth, getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT * 4, mPaint);
        // TODO: Adjust bounds
        mPaint.setColor(Color.BLACK);

        // Text
        textWidth = (int) mPaint.measureText(mText);
        textHeight = mPaint.ascent();
        canvas.drawText(mText, getPaddingTop() * 2, (getPaddingTop() + pixels * 2) / 2 + pixels / 2, mPaint);
        mPaint.setColor(Color.GRAY);

        // Lines
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), parentWidth, getPaddingTop(), mPaint); // Top line
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), parentHeight, mPaint); // Left Line
        canvas.drawLine(parentWidth, getPaddingTop(), parentWidth, parentHeight, mPaint); // Right Line
        canvas.drawLine(getPaddingLeft(), getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT * 4, parentWidth, getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT * 4, mPaint); // Bottom Line
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + pixels * 2, parentWidth, getPaddingTop() + pixels * 2, mPaint); // Dividing line

        // Drops
        Bitmap bitC = BitmapFactory.decodeResource(getResources(), R.mipmap.drop_c);
        Bitmap bitM = BitmapFactory.decodeResource(getResources(), R.mipmap.drop_m);
        Bitmap bitY = BitmapFactory.decodeResource(getResources(), R.mipmap.drop_y);
        Bitmap bitK = BitmapFactory.decodeResource(getResources(), R.mipmap.drop_k);

        DROP_HEIGHT = bitC.getHeight();

        canvas.drawBitmap(bitC, getPaddingTop(), getPaddingTop() * 2 + pixels * 2, mPaint);
        canvas.drawBitmap(bitM, getPaddingTop(), getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT, mPaint);
        canvas.drawBitmap(bitY, getPaddingTop(), getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT * 2, mPaint);
        canvas.drawBitmap(bitK, getPaddingTop(), getPaddingTop() * 2 + pixels * 2 + DROP_HEIGHT * 3, mPaint);

        // Bars
        mPaint.setColor(Color.parseColor("#78EEEB"));
        canvas.drawRect(getPaddingTop() * 2 + DROP_HEIGHT, getPaddingTop() * 2 + pixels * 2 + 5, parentWidth - getPaddingTop() * 2, getPaddingTop() * 2 + pixels * 4 - 5, mPaint);
        mPaint.setColor(Color.parseColor("#FF6AA7"));
        canvas.drawRect(getPaddingTop() * 2 + DROP_HEIGHT, getPaddingTop() * 2 + pixels * 2 + 5 + DROP_HEIGHT, parentWidth - getPaddingTop() * 2, getPaddingTop() * 2 + pixels * 4 + DROP_HEIGHT - 5, mPaint);
        mPaint.setColor(Color.parseColor("#FFDB61"));
        canvas.drawRect(getPaddingTop() * 2 + DROP_HEIGHT, getPaddingTop() * 2 + pixels * 2 + 5 + DROP_HEIGHT * 2, parentWidth - getPaddingTop() * 2, getPaddingTop() * 2 + pixels * 4 + DROP_HEIGHT * 2 - 5, mPaint);
        mPaint.setColor(Color.parseColor("#4F4F4F"));
        canvas.drawRect(getPaddingTop() * 2 + DROP_HEIGHT, getPaddingTop() * 2 + pixels * 2 + 5 + DROP_HEIGHT * 3, parentWidth - getPaddingTop() * 2, getPaddingTop() * 2 + pixels * 4 + DROP_HEIGHT * 3 - 5, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawText(String.valueOf(df.format(C * 100)) + "%", getPaddingTop() * 3 + DROP_HEIGHT, DROP_HEIGHT * 2 - getPaddingTop(), mPaint);
        canvas.drawText(String.valueOf(df.format(M * 100)) + "%", getPaddingTop() * 3 + DROP_HEIGHT, DROP_HEIGHT * 3 - getPaddingTop(), mPaint);
        canvas.drawText(String.valueOf(df.format(Y * 100)) + "%", getPaddingTop() * 3 + DROP_HEIGHT, DROP_HEIGHT * 4 - getPaddingTop(), mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(df.format(K * 100)) + "%", getPaddingTop() * 3 + DROP_HEIGHT, DROP_HEIGHT * 5 - getPaddingTop(), mPaint);
    }
    // TODO: Work on adjusting padding
}