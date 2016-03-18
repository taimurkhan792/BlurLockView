package com.nightonke.blurlockview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Weiping on 2016/3/16.
 */

public class BigButtonView extends FrameLayout {

    private FrameLayout frameLayout;
    private View clickEffect;
    private TextView text;
    private TextView subText;
    private String textString = "";
    private String subTextString = "";
    private ObjectAnimator clickEffectAnimator;
    private int duration = 500;
    private OnPressListener onPressListener;

    public BigButtonView(Context context) {
        this(context, null);
    }

    public BigButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.big_button_view, this, true);

        Resources resources = getResources();

        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);

        text = (TextView)findViewById(R.id.text);
        text.setText(textString);
        text.setTextColor(ContextCompat.getColor(context, R.color.default_big_button_text_color));
        text.setTextSize(resources.getInteger(R.integer.default_big_button_text_size));

        subText = (TextView)findViewById(R.id.sub_text);
        subText.setText(subTextString);
        subText.setTextColor(ContextCompat.getColor(context, R.color.default_big_button_sub_text_color));
        subText.setTextSize(resources.getInteger(R.integer.default_big_button_sub_text_size));

        clickEffect = findViewById(R.id.click_effect);
        clickEffect.setAlpha(0);
        clickEffectAnimator = ObjectAnimator.ofFloat(clickEffect, "alpha", 1f, 0f);
        clickEffectAnimator.setDuration(duration);
    }

    /**
     * Set the listener, for returning what happened to BlurLockView.
     *
     * @param onPressListener OnPressListener.
     */
    public void setOnPressListener(OnPressListener onPressListener) {
        this.onPressListener = onPressListener;
    }

    /**
     * Set the width of the button.
     *
     * @param width Width, in pixels.
     */
    public void setWidth(int width) {
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.width = width;
        frameLayout.setLayoutParams(layoutParams);
    }

    /**
     * Set the height of the button.
     *
     * @param height Height, in pixels.
     */
    public void setHeight(int height) {
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = height;
        frameLayout.setLayoutParams(layoutParams);
    }

    /**
     * Set the resource of background.
     *
     * @param resourceId ResourceId.
     */
    public void setBackground(int resourceId) {
        frameLayout.setBackgroundResource(resourceId);
    }

    /**
     * Set the resource of click effect.
     *
     * @param resourceId ResourceId.
     */
    public void setEffect(int resourceId) {
        clickEffect.setBackgroundResource(resourceId);
    }

    /**
     * Set the duration of the effect.
     *
     * @param duration Duration, in ms.
     */
    public void setEffectDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Set the text size of the main text.
     *
     * @param size Text size, in sp.
     */
    public void setTextSize(int size) {
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * Set the text size of the sub text.
     *
     * @param size Text size, in sp.
     */
    public void setSubTextSize(int size) {
        subText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * Set the text color of main text.
     *
     * @param color Color.
     */
    public void setTextColor(int color) {
        text.setTextColor(color);
    }

    /**
     * Set the text color of sub text.
     *
     * @param color Color.
     */
    public void setSubTextColor(int color) {
        subText.setTextColor(color);
    }

    /**
     * Set font of button.
     *
     * @param typeFace New font.
     */
    public void setTypeFace(Typeface typeFace) {
        text.setTypeface(typeFace);
        subText.setTypeface(typeFace);
    }

    /**
     * Set the string of the text.
     *
     * @param textString The new string.
     */
    public void setText(String textString) {
        this.textString = textString;
        if (text != null) text.setText(textString);
    }

    /**
     * Set the string of the sub text.
     *
     * @param subTextString The new string.
     */
    public void setSubText(String subTextString) {
        this.subTextString = subTextString;
        if (subText != null) subText.setText(subTextString);
    }

    /**
     * Set the visibility of sub textview.
     *
     * @param visibility The visibility.
     */
    public void setSubTextVisibility(int visibility) {
        if (visibility == GONE) {
            text.setGravity(Gravity.CENTER);
        } else {
            text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        }
        subText.setVisibility(visibility);
    }

    /**
     * Perform the click effect.
     *
     * @param event MotionEvent.
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (onPressListener != null) onPressListener.onPress(textString);
                clickEffectAnimator.cancel();
                clickEffect.setAlpha(1);
                break;
            case MotionEvent.ACTION_UP:
                clickEffectAnimator.start();
                break;
            default:break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * Clear the animation.
     */
    public void clearAnimation() {
        if (clickEffect.getAlpha() == 1) {
            clickEffectAnimator.cancel();
            clickEffectAnimator.start();
        }
    }

    public interface OnPressListener {
        void onPress(String string);
    }
}
