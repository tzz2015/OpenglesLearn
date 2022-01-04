package com.example.opengleslearn.data;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author baosiliang
 * @date 2020/1/15.
 */
@IntDef({
        AnimationShapeType.DEFEAT,
        AnimationShapeType.SWIRL,
        })
@Retention(SOURCE)
public @interface AnimationShapeType {
    int DEFEAT = 0;
    int SWIRL = 1;
}
