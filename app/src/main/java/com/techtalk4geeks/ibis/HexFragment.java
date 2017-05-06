package com.techtalk4geeks.ibis;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class HexFragment extends Fragment {

    TextWatcher tt = null;
    String editTextString;
    Context context;

    EditText hexET;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();

        View v = inflater.inflate(R.layout.fragment_hex, container, false);

        hexET = (EditText) v.findViewById(R.id.hexText);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tt = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                log("onTextChanged triggered");
                editTextString = charSequence.toString();
                logDebug("editTextString = " + editTextString);
            }

            public void afterTextChanged(Editable s) {
                hexET.removeTextChangedListener(this);
                if (!(editTextString.startsWith("#"))) {
                    s.insert(0, "#");
                    log("Added hashtag");
                } else {
                    for (int i = 1; i < editTextString.length(); i++) {
                        // Iterate through string and check for any extra hashtags
                        if (editTextString.substring(i, i + 1).equals("#")) {
                            s.delete(i, i + 1);
                            log("Deleting extra hashtag");
                        }
                    }
                }
                hexET.addTextChangedListener(this);
                hexET.requestFocus();
            }
        };
        hexET.addTextChangedListener(tt);

        hexET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    hexET.requestFocus();
                    if (verifyHEX(hexET.getText())) {
                        Log.i("Ibis", "Enter handled");
                        Intent colorIntent = new Intent(context, ColorDetailActivity.class);
                        Log.d("Ibis", "Putting color as " + hexET.getText().toString());
                        colorIntent.putExtra("color", hexET.getText().toString());
                        colorIntent.putExtra("format", "HEX");
                        startActivity(colorIntent);

                        handled = true;
                    }
                }
                return handled;
            }
        });
    }

    public void log(String message) {
        Log.i("Ibis", message);
    }

    public void logDebug(String message) {
        Log.d("Ibis", message);
    }

    public void logError(String message) {
        Log.e("Ibis", message);
    }

    public void logWTF(String message) {
        Log.wtf("Ibis", message);
    }

    public Boolean verifyHEX(Editable hex) {
        log("Verifying " + hex.toString());
        try {
            int color = Color.parseColor(hex.toString());
        } catch (IllegalArgumentException e) {
            logError(hex + " is not a valid HEX code.");
            Toast.makeText(context, hex + " is not a valid HEX code.", LENGTH_SHORT).show();
            // TODO: Find way to request focus
            return false;
        }
        log(hex + " is a valid HEX code.");
        return true;
    }
}