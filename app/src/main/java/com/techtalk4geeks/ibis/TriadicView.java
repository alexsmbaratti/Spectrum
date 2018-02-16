package com.techtalk4geeks.ibis;

import android.content.Context;
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

public class TriadicView extends View implements View.OnClickListener {
    public static final int TEXT_SIZE = 24;
    String colorString;
    Context context;
    float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE, getResources().getDisplayMetrics());
    String triad1 = " ";
    String triad2 = " ";
    String triad3 = " ";
    private Paint mPaint;
    private String mText = "Color Text Here";
    private int parentHeight;
    private int parentWidth;
    private int color; // Default color
    private float textWidth;
    private float textHeight;
    private String colorName = "";
    private String ticketText = "Triadic Colors";

    public TriadicView(Context context) {
        super(context);
        this.context = context;
        initTicketView();
        setOnClickListener(this);
    }

    public TriadicView(Context context, AttributeSet attrs) {
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

    public TriadicView(Context context, String colorString) {
        super(context);
        this.context = context;
        initTicketView();
        createTriad(colorString);

        setTextSize((int) pixels);
        setText(colorString.toUpperCase());
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

    public String formatHEX(String hex) {
        // Returns HEX without hashtag
        if (hex.startsWith("#")) {
            return hex.substring(1);
        } else {
            return hex;
        }
    }

    public void createTriad(String colorString) {
        triad1 = colorString;

        colorString = formatHEX(colorString);

        String rr = colorString.substring(0, 2);
        String gg = colorString.substring(2, 4);
        String bb = colorString.substring(4, 6);

        triad2 = "#" + bb + rr + gg;
        triad3 = "#" + gg + bb + rr;
    }

    public void setText(String text) {
        mText = text;
        colorString = text;
        color = Color.parseColor(colorString);
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

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(widthMeasureSpec));
    }

    public void onClick(View arg0) {
        Log.d("Ibis", "TriadicView clicked");
        // TODO: Determine which color is tapped
        Log.i("Ibis", "STATIC: " + getPaddingTop() + pixels * 4);

//        Intent colorIntent = new Intent(context, ColorDetailActivity.class);
//        colorIntent.putExtra("color", colorString);
//        colorIntent.putExtra("format", "HEX");
//        colorIntent.putExtra("name", colorName);
//        context.startActivity(colorIntent);
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
        parentHeight = (int) (pixels * 8);
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

        // Rectangle
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), parentWidth, parentHeight, mPaint);
        // TODO: Adjust bounds

        mPaint.setColor(Color.BLACK);
        float labelWidth = parentWidth * 0.75f;
        textWidth = (int) mPaint.measureText(mText);
        textHeight = mPaint.ascent();
        canvas.drawText(triad2, getPaddingTop() * 2, (pixels * 6 - textHeight) / 2, mPaint);
        canvas.drawText(triad1, getPaddingTop() * 2, (pixels * 10 - textHeight) / 2, mPaint);
        canvas.drawText(triad3, getPaddingTop() * 2, (pixels * 14 - textHeight) / 2, mPaint);
        mPaint.setColor(Color.parseColor(triad2)); // Triad 2
        canvas.drawRect(parentWidth / 2, getPaddingTop() + pixels * 2, parentWidth, getPaddingTop() + pixels * 4, mPaint);
        mPaint.setColor(Color.parseColor(triad1)); // Triad 1
        canvas.drawRect(parentWidth / 2, getPaddingTop() + pixels * 4, parentWidth, getPaddingTop() + pixels * 6, mPaint);
        mPaint.setColor(Color.parseColor(triad3)); // Triad 3
        canvas.drawRect(parentWidth / 2, getPaddingTop() + pixels * 6, parentWidth, getPaddingTop() + pixels * 8, mPaint);

        // Text
        textWidth = (int) mPaint.measureText(ticketText);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(ticketText, getPaddingTop() * 2, (getPaddingTop() + pixels * 2) / 2 + pixels / 2, mPaint);
        mPaint.setColor(Color.GRAY);

        // Lines
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), parentWidth, getPaddingTop(), mPaint); // Top line
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), parentHeight, mPaint); // Left Line
        canvas.drawLine(parentWidth, getPaddingTop(), parentWidth, parentHeight, mPaint); // Right Line
        canvas.drawLine(getPaddingLeft(), parentHeight, parentWidth, parentHeight, mPaint); // Bottom Line
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + pixels * 2, parentWidth, getPaddingTop() + pixels * 2, mPaint); // Dividing line
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + pixels * 4, parentWidth, getPaddingTop() + pixels * 4, mPaint); // Dividing line
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + pixels * 6, parentWidth, getPaddingTop() + pixels * 6, mPaint); // Dividing line
        canvas.drawLine(parentWidth / 2, getPaddingTop() + pixels * 2, parentWidth / 2, parentHeight + pixels * 2, mPaint); // Border between color and text
    }
    // TODO: Work on adjusting padding
}