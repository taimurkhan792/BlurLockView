package com.nightonke.blurlockview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Weiping on 2016/3/17.
 */
public class Dot extends FrameLayout {

    private View selected;
    private View unselected;

    private ObjectAnimator selectedAnimator;
    private ObjectAnimator unselectedAnimator;

    private boolean isSelected = false;

    public Dot(Context context) {
        this(context, null);
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.dot_view, this, true);

        selected = findViewById(R.id.selected);
        unselected = findViewById(R.id.unselected);

        clear();
    }

    /**
     * Set this dot to selected or not.
     *
     * @param isSelected Selected or not.
     */
    public void setSelected(boolean isSelected) {
        if (!(this.isSelected ^ isSelected)) return;
        this.isSelected = isSelected;
        if (isSelected) {
            // change to selected
            selected.setAlpha(0);
            unselected.setAlpha(1);
            if (selectedAnimator != null) selectedAnimator.cancel();
            if (unselectedAnimator != null) unselectedAnimator.cancel();

            selectedAnimator = ObjectAnimator.ofFloat(selected, "alpha", 0f, 1f);
            selectedAnimator.setDuration(300);
            selectedAnimator.start();

            unselectedAnimator = ObjectAnimator.ofFloat(unselected, "alpha", 1f, 0f);
            unselectedAnimator.setDuration(300);
            unselectedAnimator.start();
        } else {
            // change to unselected
            selected.setAlpha(1);
            unselected.setAlpha(0);
            if (selectedAnimator != null) selectedAnimator.cancel();
            if (unselectedAnimator != null) unselectedAnimator.cancel();

            selectedAnimator = ObjectAnimator.ofFloat(selected, "alpha", 1f, 0f);
            selectedAnimator.setDuration(300);
            selectedAnimator.start();

            unselectedAnimator = ObjectAnimator.ofFloat(unselected, "alpha", 0f, 1f);
            unselectedAnimator.setDuration(300);
            unselectedAnimator.start();
        }
    }

    /**
     * Clear the dot.
     */
    public void clear() {
        selected.setAlpha(0);
        unselected.setAlpha(1);
    }
}
