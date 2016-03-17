package com.nightonke.blurlockviewsample;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar showDuration;
    private TextView showDurationTextView;
    private SeekBar hideDuration;
    private TextView hideDurationTextView;

    private RadioButton[] showDirections = new RadioButton[4];
    private RadioButton[] hideDirections = new RadioButton[4];
    
    private RadioButton[] easeTypes = new RadioButton[31];

    private FloatingActionButton go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        showDuration = (SeekBar)findViewById(R.id.show_duration);
        showDurationTextView = (TextView)findViewById(R.id.show_duration_text);
        showDurationTextView.setText((showDuration.getProgress() * 500 + 1000) + "ms");
        showDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showDurationTextView.setText((progress * 500 + 1000) + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hideDuration = (SeekBar)findViewById(R.id.hide_duration);
        hideDurationTextView = (TextView)findViewById(R.id.hide_duration_text);
        hideDurationTextView.setText((hideDuration.getProgress() * 500 + 1000) + "ms");
        hideDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hideDurationTextView.setText((progress * 500 + 1000) + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        String[] showDirectionsArray = getResources().getStringArray(R.array.show_direction);
        for (int i = 0; i < 4; i++) {
            showDirections[i] = new RadioButton(this);
            showDirections[i].setText(showDirectionsArray[i]);
            showDirections[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) findViewById(R.id.group_show_direction)).addView(showDirections[i]);
        }
        showDirections[0].setChecked(true);

        String[] hideDirectionsArray = getResources().getStringArray(R.array.hide_direction);
        for (int i = 0; i < 4; i++) {
            hideDirections[i] = new RadioButton(this);
            hideDirections[i].setText(hideDirectionsArray[i]);
            hideDirections[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) findViewById(R.id.group_hide_direction)).addView(hideDirections[i]);
        }
        hideDirections[0].setChecked(true);

        String[] easeTypesArray = getResources().getStringArray(R.array.ease_type);
        for (int i = 0; i < 31; i++) {
            easeTypes[i] = new RadioButton(this);
            easeTypes[i].setText(easeTypesArray[i]);
            easeTypes[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) findViewById(R.id.group_ease_type)).addView(easeTypes[i]);
        }
        easeTypes[30].setChecked(true);

        go = (FloatingActionButton)findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(((EditText)findViewById(R.id.password)).getText().toString())) {
                    Toast.makeText(SettingsActivity.this,
                            R.string.password_is_empty,
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (((EditText)findViewById(R.id.password)).getText().toString().length()
                        > 10) {
                    Toast.makeText(SettingsActivity.this,
                            R.string.password_too_long,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(SettingsActivity.this, ShowActivity.class);
                intent.putExtra("PASSWORD_TYPE", getPasswordType());
                intent.putExtra("SHOW_DURATION", showDuration.getProgress() * 500 + 1000);
                intent.putExtra("HIDE_DURATION", hideDuration.getProgress() * 500 + 1000);
                intent.putExtra("SHOW_DIRECTION", getShowDirection());
                intent.putExtra("HIDE_DIRECTION", getHideDirection());
                intent.putExtra("EASE_TYPE", getEaseType());
                intent.putExtra("PASSWORD",
                        ((EditText)findViewById(R.id.password)).getText().toString());
                startActivity(intent);
            }
        });
    }

    private String getPasswordType() {
        switch (((RadioGroup)findViewById(R.id.group_password_type)).getCheckedRadioButtonId()) {
            case R.id.password_number: return "PASSWORD_NUMBER";
            case R.id.password_text: return "PASSWORD_TEXT";
        }
        return "";
    }

    private int getShowDirection() {
        for (int i = 0; i < 4; i++) if (showDirections[i].isChecked()) return i;
        return 0;
    }

    private int getHideDirection() {
        for (int i = 0; i < 4; i++) if (hideDirections[i].isChecked()) return i;
        return 0;
    }

    private int getEaseType() {
        for (int i = 0; i < 31; i++) if (easeTypes[i].isChecked()) return i;
        return 0;
    }
}
