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
                blurLockView.show(
                        getIntent().getIntExtra("SHOW_DURATION", 1000),
                        getShowType(getIntent().getIntExtra("SHOW_DIRECTION", 0)),
                        getEaseType(getIntent().getIntExtra("EASE_TYPE", 30)));
                break;
        }
    }

    private ShowType getShowType(int p) {
        ShowType showType = ShowType.FROM_TOP_TO_BOTTOM;
        switch (p) {
            case 0: showType = ShowType.FROM_TOP_TO_BOTTOM; break;
            case 1: showType = ShowType.FROM_RIGHT_TO_LEFT; break;
            case 2: showType = ShowType.FROM_BOTTOM_TO_TOP; break;
            case 3: showType = ShowType.FROM_LEFT_TO_RIGHT; break;
        }
        return showType;
    }

    private EaseType getEaseType(int p) {
        EaseType easeType = EaseType.Linear;
        switch (p) {
            case 0: easeType = EaseType.EaseInSine; break;
            case 1: easeType = EaseType.EaseOutSine; break;
            case 2: easeType = EaseType.EaseInOutSine; break;
            case 3: easeType = EaseType.EaseInQuad; break;
            case 4: easeType = EaseType.EaseOutQuad; break;
            case 5: easeType = EaseType.EaseInOutQuad; break;
            case 6: easeType = EaseType.EaseInCubic; break;
            case 7: easeType = EaseType.EaseOutCubic; break;
            case 8: easeType = EaseType.EaseInOutCubic; break;
            case 9: easeType = EaseType.EaseInQuart; break;
            case 10: easeType = EaseType.EaseOutQuart; break;
            case 11: easeType = EaseType.EaseInOutQuart; break;
            case 12: easeType = EaseType.EaseInQuint; break;
            case 13: easeType = EaseType.EaseOutQuint; break;
            case 14: easeType = EaseType.EaseInOutQuint; break;
            case 15: easeType = EaseType.EaseInExpo; break;
            case 16: easeType = EaseType.EaseOutExpo; break;
            case 17: easeType = EaseType.EaseInOutExpo; break;
            case 18: easeType = EaseType.EaseInCirc; break;
            case 19: easeType = EaseType.EaseOutCirc; break;
            case 20: easeType = EaseType.EaseInOutCirc; break;
            case 21: easeType = EaseType.EaseInBack; break;
            case 22: easeType = EaseType.EaseOutBack; break;
            case 23: easeType = EaseType.EaseInOutBack; break;
            case 24: easeType = EaseType.EaseInElastic; break;
            case 25: easeType = EaseType.EaseOutElastic; break;
            case 26: easeType = EaseType.EaseInOutElastic; break;
            case 27: easeType = EaseType.EaseInBounce; break;
            case 28: easeType = EaseType.EaseOutBounce; break;
            case 29: easeType = EaseType.EaseInOutBounce; break;
            case 30: easeType = EaseType.Linear; break;
        }
        return easeType;
    }
}
