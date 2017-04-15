package com.techtalk4geeks.ibis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioGroup group;
    RadioButton rgb;
    RadioButton hex;
    RadioButton hsl;
    RadioButton cmyk;

    int radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioButton = R.id.hex;
        group = (RadioGroup) findViewById(R.id.radioGroup);

        // Potentially redundant code
        // TODO: Reevaluate and remove if necessary
        rgb = (RadioButton) findViewById(R.id.rgb);
        hex = (RadioButton) findViewById(R.id.hex);
        hsl = (RadioButton) findViewById(R.id.hsl);
        cmyk = (RadioButton) findViewById(R.id.cmyk);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame_layout, new HexFragment());
        fragmentTransaction.commit();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Fragment fragment = null;

                switch (i) {
                    case R.id.rgb:
                        log("RGB Radio Button Triggered");
                        radioButton = R.id.rgb;
                        fragment = new RGBFragment();
                        break;
                    case R.id.hex:
                        log("Hex Radio Button Triggered");
                        radioButton = R.id.hex;
                        fragment = new HexFragment();
                        break;
                    case R.id.hsl:
                        log("HSL Radio Button Triggered");
                        radioButton = R.id.hsl;
                        fragment = new HSLFragment();
                        break;
                    case R.id.cmyk:
                        log("CMYK Radio Button Triggered");
                        radioButton = R.id.cmyk;
                        break;
                    default:
                        logWTF("Default Case in Radio Button Switch Statement Triggered");
                        radioButton = R.id.hex;
                        fragment = new HexFragment();
                        break;
                }

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_frame_layout, fragment);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.colordetail) {
            String demoColor = "#fbc69a";
            Intent colorIntent = new Intent(this, ColorDetailActivity.class);
            colorIntent.putExtra("color", demoColor);
            startActivity(colorIntent);
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
