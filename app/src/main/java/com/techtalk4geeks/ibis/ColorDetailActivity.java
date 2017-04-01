package com.techtalk4geeks.ibis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

public class ColorDetailActivity extends AppCompatActivity {

    String color;
    Color c;

    LinearLayout colorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_detail);

        colorLayout = (LinearLayout) findViewById(R.id.colorLayout);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                color = null;
            } else {
                color = extras.getString("color");
            }
        } else {
            color = (String) savedInstanceState.getSerializable("color");
        }

        log("ColorDetailActivity received extra color with value " + color);

        colorLayout.setBackgroundColor((Color.parseColor(color)));
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
