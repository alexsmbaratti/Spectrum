package com.techtalk4geeks.ibis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    TextWatcher tt = null;
    String editTextString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et = (EditText) findViewById(R.id.editText);

        tt = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editTextString = charSequence.toString();
            }

            public void afterTextChanged(Editable s) {
                et.removeTextChangedListener(this);
                if (!(editTextString.startsWith("#"))) {
                    s.insert(0, "#");
                } else {
                    // Iterate through string and check for any extra hashtags
                }
                et.addTextChangedListener(this);
            }
        };
        et.addTextChangedListener(tt);
    }

    public void log(String message) {
        Log.i("Ibis", message);
    }
}
