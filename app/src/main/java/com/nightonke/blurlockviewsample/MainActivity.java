package com.nightonke.blurlockviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.nightonke.blurlockview.BlurLockView;

public class MainActivity extends AppCompatActivity {

    private BlurLockView blurLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);
        blurLockView.setBlurredView(findViewById(R.id.image));
        blurLockView.setCorrectPassword("1234");
        blurLockView.invalidate();
    }
}
