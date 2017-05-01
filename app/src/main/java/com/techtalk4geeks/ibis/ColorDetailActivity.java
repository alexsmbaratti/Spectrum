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
    String colorName;

    String format;

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
                format = "HEX";
                colorName = "";
                Log.e("Ibis", "No extras found! Using default values.");
            } else {
                color = extras.getString("color");
                format = extras.getString("format");
                colorName = extras.getString("name");
            }
        } else {
            color = (String) savedInstanceState.getSerializable("color");
            format = (String) savedInstanceState.getSerializable("format");
            colorName = (String) savedInstanceState.getSerializable("name");

            // TODO: Implement colorName
        }

        log("ColorDetailActivity received extra color with value " + color + " in format " + format + " and with description " + colorName);

        if (!(getTitle().toString().isEmpty())) {
            setTitle(colorName);
        } else if (getTitle().toString().equals("")) {
            setTitle(color);
        }

        int colorInt;

        String parsableColor;
        int r = 0;
        int g = 0;
        int b = 0;

        switch (format) {
            case "HEX":
                colorLayout.setBackgroundColor((Color.parseColor(color)));
                colorText.setText(color.toUpperCase());

                if (color.charAt(0) == '#') {
                    parsableColor = color.substring(1);
                } else {
                    parsableColor = color;
                }
                colorInt = (int) Long.parseLong(parsableColor, 16);
                r = (colorInt >> 16) & 0xFF;
                g = (colorInt >> 8) & 0xFF;
                b = (colorInt >> 0) & 0xFF;

                // TODO: Ensure that demo still works

                break;
            case "RGB":
                // TODO: Add more formats
                break;
            default:
        }

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
