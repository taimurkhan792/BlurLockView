package com.nightonke.blurlockview.Directions;

/**
 * Created by Weiping on 2016/3/17.
 */
public enum ShowType {

    FROM_TOP_TO_BOTTOM(0),
    FROM_RIGHT_TO_LEFT(1),
    FROM_BOTTOM_TO_TOP(2),
    FROM_LEFT_TO_RIGHT(3),
    FADE_IN(4);

    int type;

    ShowType(int type) {
        this.type = type;
    }

}
