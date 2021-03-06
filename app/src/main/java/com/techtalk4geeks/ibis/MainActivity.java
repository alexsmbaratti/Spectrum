package com.techtalk4geeks.ibis;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    RadioGroup group;
    RadioButton rgb;
    RadioButton hex;
    RadioButton hsl;
    RadioButton cmyk;
    ScrollView scrollView;
    LinearLayout ticketLayout;

    SharedPreferences prefs;
    Set<String> colors;

    int radioButton;

    public static void logInstalledAccessiblityServices(Context context) {

        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getInstalledAccessibilityServiceList();
        for (AccessibilityServiceInfo service : runningServices) {
            Log.i("Ibis", "SERVICE: " + service.getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        radioButton = R.id.hex;
        group = (RadioGroup) findViewById(R.id.radioGroup);
        scrollView = (ScrollView) findViewById(R.id.mainScrollView);
        ticketLayout = (LinearLayout) findViewById(R.id.ticketLayout);

        prefs = this.getSharedPreferences(
                "com.techtalk4geeks.ibis", Context.MODE_PRIVATE);
        colors = prefs.getStringSet("com.techtalk4geeks.ibis.colors", new HashSet<String>());

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

        ArrayList<View> ticketList = new ArrayList<>();

        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "This is a development build", Toast.LENGTH_SHORT).show();
        }

        Space space = new Space(this);
        space.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));

        if (colors.size() > 0) {
            try {
                for (Iterator<String> iterator = colors.iterator(); iterator.hasNext(); ) {
                    String[] output = iterator.next().split("/");
                    ticketList.add(new TicketView(this, output[0]));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            TextView explanation = new TextView(this);
            explanation.setText("Colors you save as favorites will appear here."); // Maybe make this recents and create a separate area for favorites?
            explanation.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ticketList.add(explanation);
        }

        ticketList.add(space);

        for (int i = 0; i < ticketList.size(); i++) {
            ticketList.get(i).setElevation((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
            ticketLayout.addView(ticketList.get(i));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
