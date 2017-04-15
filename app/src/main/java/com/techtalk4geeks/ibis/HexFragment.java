package com.techtalk4geeks.ibis;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
            }
        };
        hexET.addTextChangedListener(tt);

        hexET.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                log("keyCode = " + keyCode);
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.i("Ibis", "Enter");
                    Intent colorIntent = new Intent(context, ColorDetailActivity.class);
                    colorIntent.putExtra("color", hexET.getText());
                    startActivity(colorIntent);
                    return true;
                }
                return false;
            }
        });

        hexET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    Log.i("Ibis", "Enter");
                    Intent colorIntent = new Intent(context, ColorDetailActivity.class);
                    colorIntent.putExtra("color", hexET.getText());
                    startActivity(colorIntent);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public Boolean isValid(String hex) {
        return false;
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