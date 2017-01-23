package com.techtalk4geeks.ibis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    TextWatcher tt = null;
    String editTextString;
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
        final EditText et = (EditText) findViewById(R.id.editText);
        group = (RadioGroup) findViewById(R.id.radioGroup);
        rgb = (RadioButton) findViewById(R.id.rgb);
        hex = (RadioButton) findViewById(R.id.hex);
        hsl = (RadioButton) findViewById(R.id.hsl);
        cmyk = (RadioButton) findViewById(R.id.cmyk);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rgb:
                        radioButton = R.id.rgb;
                        break;
                    case R.id.hex:
                        radioButton = R.id.hex;
                        break;
                    case R.id.hsl:
                        radioButton = R.id.hsl;
                        break;
                    case R.id.cmyk:
                        radioButton = R.id.cmyk;
                        break;
                    default:
                        radioButton = R.id.hex;
                        break;
                }
            }
        });

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
                et.removeTextChangedListener(this);
                if (radioButton == R.id.hex) {
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
                }
                et.addTextChangedListener(this);
            }
        };
        et.addTextChangedListener(tt);
    }

    public void log(String message) {
        Log.i("Ibis", message);
    }

    public void logDebug(String message) {
        Log.d("Ibis", message);
    }
}
