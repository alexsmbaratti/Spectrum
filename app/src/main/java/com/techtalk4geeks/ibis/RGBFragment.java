package com.techtalk4geeks.ibis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RGBFragment extends android.app.Fragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        context = container.getContext();

        return inflater.inflate(
                R.layout.fragment_rgb, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText rText = (EditText) getView().findViewById(R.id.rText);
        rText.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        final EditText gText = (EditText) getView().findViewById(R.id.gText);
        gText.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        final EditText bText = (EditText) getView().findViewById(R.id.bText);
        bText.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        rText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = rText.getText().length();

                if (textlength1 >= 3) {
                    gText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        gText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = gText.getText().length();

                if (textlength2 >= 3) {
                    Log.d("Ibis", "Requesting bText focus");
                    bText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        rText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    Log.d("Ibis", "rText onKeyListener Triggered\nkeyCode: " + keyCode + "\nKeyEvent: " + event.toString());
                    gText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        gText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    Log.d("Ibis", "gText onKeyListener Triggered\nkeyCode: " + keyCode + "\nKeyEvent: " + event.toString());
                    bText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        bText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    Intent colorIntent = new Intent(context, ColorDetailActivity.class);
                    colorIntent.putExtra("color", Color.rgb(Integer.parseInt(rText.getText().toString()), Integer.parseInt(gText.getText().toString()), Integer.parseInt(bText.getText().toString())));
                    colorIntent.putExtra("format", "RGB");
                    startActivity(colorIntent);
                    return true;
                }
                return false;
            }
        });
    }
}