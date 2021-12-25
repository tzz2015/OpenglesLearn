package com.example.opengleslearn.animation;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author baosiliang
 * @date 2020/1/15.
 */
@IntDef({
        MoveAnimationType.LEFT,
        MoveAnimationType.RIGHT,
        MoveAnimationType.TOP,
        MoveAnimationType.BOTTOM})
@Retention(SOURCE)
public @interface MoveAnimationType {
    int LEFT = 0;
    int RIGHT = 1;
    int TOP = 2;
    int BOTTOM = 3;
}
