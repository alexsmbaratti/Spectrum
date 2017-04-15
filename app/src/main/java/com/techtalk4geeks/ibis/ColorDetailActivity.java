package com.techtalk4geeks.ibis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorDetailActivity extends AppCompatActivity {

    String color;
    Color c;

    LinearLayout colorLayout;
    TextView colorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_detail);

        colorLayout = (LinearLayout) findViewById(R.id.colorLayout);
        colorText = (TextView) findViewById(R.id.currentColor);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                color = "#FFFFFF";
            } else {
                color = extras.getString("color");
            }
        } else {
            color = (String) savedInstanceState.getSerializable("color");
        }

        log("ColorDetailActivity received extra color with value " + color);

        colorLayout.setBackgroundColor((Color.parseColor(color)));
        colorText.setText(color.toUpperCase());

        int colorInt;

        String parsableColor;

        if (color.charAt(0) == '#') {
            parsableColor = color.substring(1);
        } else {
            parsableColor = color;
        }
        colorInt = (int) Long.parseLong(parsableColor, 16);
        int r = (colorInt >> 16) & 0xFF;
        int g = (colorInt >> 8) & 0xFF;
        int b = (colorInt >> 0) & 0xFF;

        int textColor;

        if ((r * 0.299 + g * 0.587 + b * 0.114) > 186) {
            textColor = Color.BLACK;
        } else {
            textColor = Color.WHITE;
        }

        colorText.setTextColor(textColor);
    }

    public void log(String message) {
        Log.i("Ibis", message);
    }

    public void logDebug(String message) {
        Log.d("Ibis", message);
    }

    public void logWTF(String message) {
        Log.wtf("Ibis", message);
    }
}
