package com.nightonke.blurlockviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;

public class ShowActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        BlurLockView.OnPasswordInputListener {

    private BlurLockView blurLockView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show);

        imageView = (ImageView)findViewById(R.id.image);

        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);
        blurLockView.setBlurredView(imageView);
        blurLockView.setCorrectPassword("1234");
        blurLockView.setTranslationY(blurLockView.getHeight());

        findViewById(R.id.show).setOnClickListener(this);
    }

    @Override
    public void correct(String inputPassword) {

    }

    @Override
    public void incorrect(String inputPassword) {

    }

    @Override
    public void input(String inputPassword) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                blurLockView.show(1500, ShowType.FROM_LEFT_TO_RIGHT, EaseType.EaseInBounce);
                break;
        }
    }
}
