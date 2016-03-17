package com.nightonke.blurlockview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by Weiping on 2016/3/16.
 */
public class BlurLockView extends FrameLayout
        implements BigButtonView.OnPressListener {

    private Password type = Password.NUMBER;

    private int passwordLength = 4;
    private String correctPassword = null;
    private int errorInputTimes = 0;
    private boolean returnInputPasswordAlways = false;
    private boolean autoQuit = true;
    private Typeface typeface;

    private Stack<String> passwordStack = new Stack<>();

    private Indicator indicator;

    private BigButtonView[] bigButtonViews;
    private BlurView mBlurView;
    private TextView leftButton;
    private TextView rightButton;

    private OnLeftButtonClickListener onLeftButtonClickListener;
    private OnPasswordInputListener onPasswordInputListener;

    public BlurLockView(Context context) {
        this(context, null);
    }

    public BlurLockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (type.equals(Password.NUMBER)) {
            // is number password
            LayoutInflater.from(context).inflate(R.layout.number_blur_lock_view, this, true);

            bigButtonViews = new BigButtonView[10];
            bigButtonViews[0] = (BigButtonView)findViewById(R.id.button_0);
            bigButtonViews[1] = (BigButtonView)findViewById(R.id.button_1);
            bigButtonViews[2] = (BigButtonView)findViewById(R.id.button_2);
            bigButtonViews[3] = (BigButtonView)findViewById(R.id.button_3);
            bigButtonViews[4] = (BigButtonView)findViewById(R.id.button_4);
            bigButtonViews[5] = (BigButtonView)findViewById(R.id.button_5);
            bigButtonViews[6] = (BigButtonView)findViewById(R.id.button_6);
            bigButtonViews[7] = (BigButtonView)findViewById(R.id.button_7);
            bigButtonViews[8] = (BigButtonView)findViewById(R.id.button_8);
            bigButtonViews[9] = (BigButtonView)findViewById(R.id.button_9);

            String[] texts = getResources().getStringArray(R.array.default_big_button_text);
            String[] subTexts = getResources().getStringArray(R.array.default_big_button_sub_text);
            for (int i = 0; i < 10; i++) {
                bigButtonViews[i].setOnPressListener(this);
                bigButtonViews[i].setText(texts[i]);
                bigButtonViews[i].setSubText(subTexts[i]);
            }

            bigButtonViews[0].setSubTextVisibility(View.GONE);
            bigButtonViews[1].setSubTextVisibility(View.INVISIBLE);
        }

        mBlurView = (BlurView)findViewById(R.id.blurview);
        
        Resources resources = getResources();

        indicator = (Indicator)findViewById(R.id.indicator);
        indicator.setPasswordLength(4);
        
        leftButton = (TextView)findViewById(R.id.left_button);
        leftButton.setTextColor(ContextCompat.getColor(context, R.color.default_left_button_text_color));
        leftButton.setTextSize(resources.getInteger(R.integer.default_left_button_text_size));
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftButtonClickListener != null) onLeftButtonClickListener.onClick();
            }
        });

        rightButton = (TextView)findViewById(R.id.right_button);
        rightButton.setTextColor(ContextCompat.getColor(context, R.color.default_right_button_text_color));
        rightButton.setTextSize(resources.getInteger(R.integer.default_right_button_text_size));
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordStack.size() > 0) {
                    passwordStack.pop();
                    indicator.setNumber(passwordStack.size());
                }
            }
        });
    }

    public void setBlurredView(View blurredView) {
        mBlurView.setBlurredView(blurredView);
    }

    @Override
    public void onPress(String string) {
        if (correctPassword == null) {
            throw new RuntimeException("The correct password has NOT been set!");
        }
        if (passwordStack.size() >= passwordLength) return;
        passwordStack.push(string);
        indicator.setNumber(passwordStack.size());
        StringBuilder nowPassword = new StringBuilder("");
        for (String s : passwordStack) {
            nowPassword.append(s);
        }
        String nowPasswordString = nowPassword.toString();
        if (correctPassword.equals(nowPasswordString)) {
            // correct password
            if (onPasswordInputListener != null)
                onPasswordInputListener.correct(nowPasswordString);
            if (autoQuit) {
                // perform the quit animation
            } else {

            }
        } else {
            if (correctPassword.length() > nowPasswordString.length()) {
                // input right now
                if (onPasswordInputListener != null)
                    onPasswordInputListener.input(nowPasswordString);
            } else {
                // incorrect password
                if (onPasswordInputListener != null)
                    onPasswordInputListener.incorrect(nowPasswordString);
                // perform the clear animation
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) return true;
        return super.dispatchTouchEvent(event);
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        if (type.equals(Password.NUMBER)) {
            for (int i = 0; i < 10; i++) bigButtonViews[i].setTypeFace(typeface);
        }
        leftButton.setTypeface(typeface);
        rightButton.setTypeface(typeface);
    }

    public void setType(Password type) {
        this.type = type;
    }

    public void setCorrectPassword(String correctPassword) {
        this.correctPassword = correctPassword;
    }

    public void setErrorInputTimes(int errorInputTimes) {
        this.errorInputTimes = errorInputTimes;
    }

    public void setReturnInputPasswordAlways(boolean returnInputPasswordAlways) {
        this.returnInputPasswordAlways = returnInputPasswordAlways;
    }

    public void setAutoQuit(boolean autoQuit) {
        this.autoQuit = autoQuit;
    }

    public interface OnPasswordInputListener {
        void correct(String inputPassword);
        void incorrect(String inputPassword);
        void input(String inputPassword);
    }

    public interface OnLeftButtonClickListener {
        void onClick();
    }
}
