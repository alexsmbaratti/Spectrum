package com.techtalk4geeks.ibis;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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
    Color cColor;
    String colorName;
    int inverseColor;
    Boolean isFavorite = false;

    String format;

    String parsableColor;

    LinearLayout colorLayout;
    TextView colorText;
    TextView colorDescription;
    ScrollView scrollView;
    LinearLayout colorScrollLayout;

    private String setColorString = "";

    private Menu menu;

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

        float r = 0;
        float g = 0;
        float b = 0;

        float c = 0;
        float m = 0;
        float y = 0;
        float k = 0;

        colorLayout.setBackgroundColor((Color.parseColor(color))); // TODO: Change text based on format
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

        Log.d("Ibis", "r = " + r);
        Log.d("Ibis", "g = " + g);
        Log.d("Ibis", "b = " + b);

        float max = Math.max(Math.max(r / 255, g / 255), b / 255);
        k = 1 - max;
        if (k != 1) {
            c = (1 - (r / 255) - k) / (1 - k);
            m = (1 - (g / 255) - k) / (1 - k);
            y = (1 - (b / 255) - k) / (1 - k);
        } else {
            c = 0;
            m = 0;
            y = 0;
        }

        if (parsableColor.equalsIgnoreCase("FFFFFF") && colorName.isEmpty()) {
            colorName = "True White";
        }

        // TODO: Ensure that demo still works

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + parsableColor)));
//                getWindow().setStatusBarColor(Color.parseColor("#" + parsableColor));

        int textColor;
        if ((r * 0.299 + g * 0.587 + b * 0.114) > 186) {
            textColor = Color.BLACK;
        } else {
            textColor = Color.WHITE;
        }

        // TODO: Fix algorithm for CMYK calculation

        Log.i("Ibis", "Color = " + color);
        colorScrollLayout.addView(new CMYKView(this, c, m, y, k));
        colorScrollLayout.addView(new TicketTextView(this, String.format("#%02x%02x%02x", 255 - (int) r, 255 - (int) g, 255 - (int) b), "Inverse"));
        TriadicView t = new TriadicView(this, color);
        colorScrollLayout.addView(t);
//        t.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.i("Ibis", "Touch coordinates : " +
//                            String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
//                }
//                return true;
//            }
//        });

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
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favorite) {
            if (!isFavorite) { // If the color is not currently a favorite
                menu.getItem(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.favorite, null)); // Make the icon filled in
                isFavorite = true;
            } else {
                menu.getItem(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.not_favorite, null));
                isFavorite = false;
            }
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
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.colorLayout);
                    if (linearLayout.getChildAt(1) != colorDescription) {
                        linearLayout.addView(colorDescription);
                    }
                    if (input.getText().toString().isEmpty()) {
                        linearLayout.removeView(colorDescription);
                    }
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
        if (id == R.id.palette) {
            // TODO: Implement
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
