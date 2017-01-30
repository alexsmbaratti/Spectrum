package com.techtalk4geeks.ibis;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class HexFragment extends Fragment {

    TextWatcher tt = null;
    String editTextString;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_hex, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText hexET = (EditText) getView().findViewById(R.id.hexText);

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