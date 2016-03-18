package com.nightonke.blurlockviewsample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Directions.HideType;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;
import com.nightonke.blurlockview.Password;

public class ShowActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        BlurLockView.OnPasswordInputListener,
        BlurLockView.OnLeftButtonClickListener,
        ColorChooserDialog.ColorCallback {

    private BlurLockView blurLockView;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show);

        imageView1 = (ImageView)findViewById(R.id.image_1);

        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);

        // Set the view that need to be blurred
        blurLockView.setBlurredView(imageView1);

        // Set the password
        blurLockView.setCorrectPassword(getIntent().getStringExtra("PASSWORD"));

        blurLockView.setTitle(getIntent().getStringExtra("TITLE"));
        blurLockView.setLeftButton(getIntent().getStringExtra("LEFT_BUTTON"));
        blurLockView.setRightButton(getIntent().getStringExtra("RIGHT_BUTTON"));
        blurLockView.setTypeface(getTypeface());
        blurLockView.setType(getPasswordType(), false);

        blurLockView.setOnLeftButtonClickListener(this);
        blurLockView.setOnPasswordInputListener(this);

        imageView1.setOnClickListener(this);
    }

    private Password getPasswordType() {
        if ("PASSWORD_NUMBER".equals(getIntent().getStringExtra("PASSWORD_TYPE")))
            return Password.NUMBER;
        else if ("PASSWORD_NUMBER".equals(getIntent().getStringExtra("PASSWORD_TYPE")))
            return Password.TEXT;
        return Password.NUMBER;
    }

    private Typeface getTypeface() {
        if ("SAN".equals(getIntent().getStringExtra("TYPEFACE")))
            return Typeface.createFromAsset(getAssets(),"fonts/San Francisco Regular.ttf");
        else if ("DEFAULT".equals(getIntent().getStringExtra("TYPEFACE")))
            return Typeface.DEFAULT;
        return Typeface.DEFAULT;
    }

    @Override
    public void correct(String inputPassword) {
        Toast.makeText(this,
                R.string.password_correct,
                Toast.LENGTH_SHORT).show();
        blurLockView.hide(
                getIntent().getIntExtra("HIDE_DURATION", 1000),
                getHideType(getIntent().getIntExtra("HIDE_DIRECTION", 0)),
                getEaseType(getIntent().getIntExtra("HIDE_EASE_TYPE", 30)));
    }

    @Override
    public void incorrect(String inputPassword) {
        Toast.makeText(this,
                R.string.password_incorrect,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void input(String inputPassword) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_1:
                blurLockView.show(
                        getIntent().getIntExtra("SHOW_DURATION", 1000),
                        getShowType(getIntent().getIntExtra("SHOW_DIRECTION", 0)),
                        getEaseType(getIntent().getIntExtra("SHOW_EASE_TYPE", 30)));
                break;
        }
    }

    @Override
    public void onClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.operations)
                .items(R.array.operations)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                blurLockView.show(
                                        getIntent().getIntExtra("SHOW_DURATION", 1000),
                                        getShowType(getIntent().getIntExtra("SHOW_DIRECTION", 0)),
                                        getEaseType(getIntent().getIntExtra("SHOW_EASE_TYPE", 30)));
                                break;
                            case 1:
                                blurLockView.hide(
                                        getIntent().getIntExtra("HIDE_DURATION", 1000),
                                        getHideType(getIntent().getIntExtra("HIDE_DIRECTION", 0)),
                                        getEaseType(getIntent().getIntExtra("HIDE_EASE_TYPE", 30)));
                                break;
                            case 2:
                                setBlurRadius();
                                break;
                            case 3:
                                setDownsamepleFactor();
                                break;
                            case 4:
                                setOverlayColor();
                                break;
                            case 5:
                                if (Password.NUMBER.equals(blurLockView.getType())) {
                                    blurLockView.setType(Password.TEXT, true);
                                } else if (Password.TEXT.equals(blurLockView.getType())) {
                                    blurLockView.setType(Password.NUMBER, true);
                                }
                                break;

                        }
                        return true;
                    }
                })
                .show();
    }

    private int radius;
    private void setBlurRadius() {
        new MaterialDialog.Builder(this)
                .title(R.string.set_blur_radius_title)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .positiveText(R.string.ok)
                .alwaysCallInputCallback()
                .input(
                        "[1, 20]",
                        blurLockView.getBlurRadius() + "",
                        new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        radius = -1;
                        try {
                            radius = Integer.parseInt(String.valueOf(input));
                        } catch (NumberFormatException n) {
                            radius = -1;
                        }
                        if (!(1 <= radius && radius <= 20))
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        else
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                    }
                })
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            blurLockView.setBlurRadius(radius);
                        }
                    }
                })
                .show();
    }

    private int downsamepleFactor;
    private void setDownsamepleFactor() {
        new MaterialDialog.Builder(this)
                .title(R.string.set_downsample_factor_title)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .positiveText(R.string.ok)
                .alwaysCallInputCallback()
                .input(
                        "[1, 20]",
                        blurLockView.getDownsampleFactor() + "",
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                downsamepleFactor = -1;
                                try {
                                    downsamepleFactor = Integer.parseInt(String.valueOf(input));
                                } catch (NumberFormatException n) {
                                    downsamepleFactor = -1;
                                }
                                if (!(1 <= downsamepleFactor && downsamepleFactor <= 20))
                                    dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                                else
                                    dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            }
                        })
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            blurLockView.setDownsampleFactor(downsamepleFactor);
                        }
                    }
                })
                .show();
    }

    private void setOverlayColor() {
        new ColorChooserDialog.Builder(this, R.string.set_overlay_color)
                .titleSub(R.string.set_overlay_color)
                .doneButton(R.string.ok)
                .preselect(blurLockView.getOverlayColor())
                .show();
    }

    private ShowType getShowType(int p) {
        ShowType showType = ShowType.FROM_TOP_TO_BOTTOM;
        switch (p) {
            case 0: showType = ShowType.FROM_TOP_TO_BOTTOM; break;
            case 1: showType = ShowType.FROM_RIGHT_TO_LEFT; break;
            case 2: showType = ShowType.FROM_BOTTOM_TO_TOP; break;
            case 3: showType = ShowType.FROM_LEFT_TO_RIGHT; break;
            case 4: showType = ShowType.FADE_IN; break;
        }
        return showType;
    }

    private HideType getHideType(int p) {
        HideType hideType = HideType.FROM_TOP_TO_BOTTOM;
        switch (p) {
            case 0: hideType = HideType.FROM_TOP_TO_BOTTOM; break;
            case 1: hideType = HideType.FROM_RIGHT_TO_LEFT; break;
            case 2: hideType = HideType.FROM_BOTTOM_TO_TOP; break;
            case 3: hideType = HideType.FROM_LEFT_TO_RIGHT; break;
            case 4: hideType = HideType.FADE_OUT; break;
        }
        return hideType;
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

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        blurLockView.setOverlayColor(selectedColor);
    }
}
