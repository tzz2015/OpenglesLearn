package com.example.opengleslearn.data;

import androidx.annotation.IntDef;

/**
 * @description: 动画速率变化类型
 * @author: 刘宇飞
 * @date :   2022/1/4 10:01
 */
@IntDef({
        AnimationSpeedType.LINEAR,
        AnimationSpeedType.EASE_IN_QUAD,
        AnimationSpeedType.EASE_IN_CUBIC,
        AnimationSpeedType.EASE_IN_QUART,
        AnimationSpeedType.EASE_OUT_QUINT
})
public @interface AnimationSpeedType {
    // 线性
    int LINEAR = 0;
    // 先慢后快
    int EASE_IN_QUAD = 1;
    // 先很慢后快
    int EASE_IN_CUBIC = 2;
    // 先很快后慢
    int EASE_OUT_CUBIC = 3;
    // 先很很慢后快
    int EASE_IN_QUART = 4;
    // 先很很快后慢
    int EASE_OUT_QUINT = 5;
}
