package com.techtalk4geeks.ibis;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ColorDetailActivity extends AppCompatActivity {

    String color;
    Color c;
    String colorName;
    int inverseColor;

    String format;

    LinearLayout colorLayout;
    TextView colorText;
    TextView colorDescription;
    ScrollView scrollView;
    LinearLayout colorScrollLayout;

    private String setColorString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_detail);

        colorLayout = (LinearLayout) findViewById(R.id.colorLayout);
        colorScrollLayout = (LinearLayout) findViewById(R.id.colorScrollLayout);
        colorText = (TextView) findViewById(R.id.currentColor);
        colorDescription = (TextView) findViewById(R.id.description);
        scrollView = (ScrollView) findViewById(R.id.colorScroll);

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
        }

        // TODO: Create color keyboard

        if (colorName == null) {
            colorName = "";
        }

        log("ColorDetailActivity received extra color with value " + color + " in format " + format + " and with description " + colorName);

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

                if (parsableColor.equalsIgnoreCase("FFFFFF") && colorName.isEmpty()) {
                    colorName = "True White";
                }

                // TODO: Ensure that demo still works

                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + parsableColor)));
//                getWindow().setStatusBarColor(Color.parseColor("#" + parsableColor));

                break;
            case "RGB":
                // TODO: Add more formats
                break;
            default:
                // TODO: Ensure actionBar changes color in all cases
        }

        int textColor;
        if ((r * 0.299 + g * 0.587 + b * 0.114) > 186) {
            textColor = Color.BLACK;
        } else {
            textColor = Color.WHITE;
        }

        colorScrollLayout.addView(new TicketView(this, String.format("#%02x%02x%02x", 255 - r, 255 - g, 255 - b)));

        colorText.setTextColor(textColor);
        colorDescription.setTextColor(textColor);
        colorDescription.setText(colorName);

        if ((colorName.isEmpty())) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.colorLayout);
            linearLayout.removeView(colorDescription);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_color_detail, menu);
        // TODO: Fix menu inflater
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favorite) {
            MenuItem favorite = (MenuItem) findViewById(R.id.favorite);
            favorite.setIcon(R.drawable.favorite);
            // TODO: Fix crash
            return true;
        }
        if (id == R.id.rename) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Description");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint(colorDescription.getText());
            int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
            input.setPadding(pixels, pixels, pixels, pixels);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setColorString = input.getText().toString();
                    colorDescription.setText(setColorString);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
