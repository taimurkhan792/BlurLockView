package com.nightonke.blurlockviewsample;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar showDuration;
    private TextView showDurationTextView;

    private RadioButton[] showDirections = new RadioButton[4];
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
                Intent intent = new Intent(SettingsActivity.this, ShowActivity.class);
                intent.putExtra("PASSWORD_TYPE", getPasswordType());
                intent.putExtra("SHOW_DURATION", showDuration.getProgress() * 500 + 1000);
                intent.putExtra("SHOW_DIRECTION", getShowDirection());
                intent.putExtra("EASE_TYPE", getEaseType());
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

    private int getEaseType() {
        for (int i = 0; i < 31; i++) if (easeTypes[i].isChecked()) return i;
        return 0;
    }
}
