package com.nightonke.blurlockview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

import com.nightonke.blurlockview.Directions.HideType;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;

import java.util.Stack;

/**
 * Created by Weiping on 2016/3/16.
 */
public class BlurLockView extends FrameLayout
        implements BigButtonView.OnPressListener {

    private Password type = Password.NUMBER;

    private int passwordLength = 4;
    private String correctPassword = null;
    private int incorrectInputTimes = 0;
    private Typeface typeface;

    private boolean animationIsPlaying = false;

    private Stack<String> passwordStack = new Stack<>();

    private TextView title;
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
        mBlurView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        Resources resources = getResources();

        indicator = (Indicator)findViewById(R.id.indicator);
        indicator.setPasswordLength(passwordLength);

        title = (TextView)findViewById(R.id.title);
        title.setTextColor(ContextCompat.getColor(context, R.color.default_title_text_color));
        title.setTextSize(resources.getInteger(R.integer.default_title_text_size));
        
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
                    indicator.delete();
                }
            }
        });
    }

    /**
     * Set the view that need to be blurred.
     *
     * @param blurredView The view.
     */
    public void setBlurredView(View blurredView) {
        mBlurView.setBlurredView(blurredView);
    }

    /**
     * Set the listener.
     *
     * @param onLeftButtonClickListener Listener.
     */
    public void setOnLeftButtonClickListener(OnLeftButtonClickListener onLeftButtonClickListener) {
        this.onLeftButtonClickListener = onLeftButtonClickListener;
    }

    /**
     * Set the listener.
     *
     * @param onPasswordInputListener Listener.
     */
    public void setOnPasswordInputListener(OnPasswordInputListener onPasswordInputListener) {
        this.onPasswordInputListener = onPasswordInputListener;
    }

    /**
     * From the button views.
     *
     * @param string The string from button views.
     */
    @Override
    public void onPress(String string) {
        if (correctPassword == null) {
            throw new RuntimeException("The correct password has NOT been set!");
        }
        if (passwordStack.size() >= passwordLength) return;
        passwordStack.push(string);
        indicator.add();
        StringBuilder nowPassword = new StringBuilder("");
        for (String s : passwordStack) {
            nowPassword.append(s);
        }
        String nowPasswordString = nowPassword.toString();
        if (correctPassword.equals(nowPasswordString)) {
            // correct password
            if (onPasswordInputListener != null)
                onPasswordInputListener.correct(nowPasswordString);
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
                indicator.clear();
                passwordStack.clear();
            }
        }
    }

    /**
     * Prevent click 2 or above buttons at the same time.
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) return true;
        return super.dispatchTouchEvent(event);
    }

    /**
     * Set all the fonts.
     *
     * @param typeface
     */
    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        if (type.equals(Password.NUMBER)) {
            for (int i = 0; i < 10; i++) bigButtonViews[i].setTypeFace(typeface);
        }
        title.setTypeface(typeface);
        leftButton.setTypeface(typeface);
        rightButton.setTypeface(typeface);
    }

    /**
     * Set all the text color.
     *
     * @param color
     */
    public void setTextColor(int color) {
        if (type.equals(Password.NUMBER)) {
            for (int i = 0; i < 10; i++) {
                bigButtonViews[i].setTextColor(color);
                bigButtonViews[i].setSubTextColor(color);
            }
        }
        title.setTextColor(color);
        leftButton.setTextColor(color);
        rightButton.setTextColor(color);
    }

    /**
     * Set the length of the password.
     * Default length is 4.
     *
     * @param passwordLength
     */
    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        indicator.setPasswordLength(passwordLength);
        passwordStack.clear();
        correctPassword = null;
    }

    /**
     * Set the password type.
     *
     * @param type Number or text.
     */
    public void setType(Password type) {
        this.type = type;
    }

    /**
     * Set the target password.
     *
     * @param correctPassword The target password.
     */
    public void setCorrectPassword(String correctPassword) {
        setPasswordLength(correctPassword.length());
        this.correctPassword = correctPassword;
    }

    /**
     * You can use this to reset the incorrect input times.
     *
     * @param incorrectInputTimes The incorrect input times.
     */
    public void setIncorrectInputTimes(int incorrectInputTimes) {
        this.incorrectInputTimes = incorrectInputTimes;
    }

    /**
     * Return the incorrect input times.
     *
     * @return Incorrect input times.
     */
    public int getIncorrectInputTimes() {
        return incorrectInputTimes;
    }

    /**
     * Invalidate the blur view.
     */
    public void update() {
        mBlurView.invalidate();
    }

    /**
     * Show this BlurLockView.
     *
     * @param duration Duration, in ms.
     * @param showType Direction, in ShowType.
     * @param easeType Ease type, in EaseType.
     */
    public void show(int duration, ShowType showType, EaseType easeType) {
        if (animationIsPlaying) return;
        animationIsPlaying = true;
        indicator.clear();
        passwordStack.clear();
        ObjectAnimator animator = null;
        setVisibility(VISIBLE);
        if (showType.equals(ShowType.FROM_TOP_TO_BOTTOM)) {
            animator = ObjectAnimator.ofFloat(this, "translationY",
                    getTranslationY() - getHeight(),
                    getTranslationY());
        } else if (showType.equals(ShowType.FROM_RIGHT_TO_LEFT)) {
            animator = ObjectAnimator.ofFloat(this, "translationX",
                    getTranslationX() + getWidth(),
                    getTranslationX());
        } else if (showType.equals(ShowType.FROM_BOTTOM_TO_TOP)) {
            animator = ObjectAnimator.ofFloat(this, "translationY",
                    getTranslationY() + getHeight(),
                    getTranslationY());
        } else if (showType.equals(ShowType.FROM_LEFT_TO_RIGHT)) {
            animator = ObjectAnimator.ofFloat(this, "translationX",
                    getTranslationX() - getWidth(),
                    getTranslationX());
        }
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                update();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationIsPlaying = false;
            }
        });
        animator.setInterpolator(InterpolatorFactory.getInterpolator(easeType));
        animator.start();
    }

    /**
     * Hide this BlurLockView.
     *
     * @param duration Duration, in ms.
     * @param hideType Direction, in HideType.
     * @param easeType Ease type, in EaseType.
     */
    public void hide(int duration, HideType hideType, EaseType easeType) {
        if (animationIsPlaying) return;
        animationIsPlaying = true;
        ObjectAnimator animator = null;
        final float originalX = getTranslationX();
        final float originalY = getTranslationY();
        if (hideType.equals(HideType.FROM_TOP_TO_BOTTOM)) {
            animator = ObjectAnimator.ofFloat(this, "translationY",
                    getTranslationY(),
                    getTranslationY() + getHeight());
        } else if (hideType.equals(HideType.FROM_RIGHT_TO_LEFT)) {
            animator = ObjectAnimator.ofFloat(this, "translationX",
                    getTranslationX(),
                    getTranslationX() - getWidth());
        } else if (hideType.equals(HideType.FROM_BOTTOM_TO_TOP)) {
            animator = ObjectAnimator.ofFloat(this, "translationY",
                    getTranslationY(),
                    getTranslationY() - getHeight());
        } else if (hideType.equals(HideType.FROM_LEFT_TO_RIGHT)) {
            animator = ObjectAnimator.ofFloat(this, "translationX",
                    getTranslationX(),
                    getTranslationX() + getWidth());
        }
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                update();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(INVISIBLE);
                setTranslationX(originalX);
                setTranslationY(originalY);
                animationIsPlaying = false;
            }
        });
        animator.setInterpolator(InterpolatorFactory.getInterpolator(easeType));
        animator.start();
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
