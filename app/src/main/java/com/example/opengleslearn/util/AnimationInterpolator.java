package com.example.opengleslearn.util;

/**
 * @description:
 * @author: 刘宇飞
 * @date :   2021/12/29 10:51
 */
public class AnimationInterpolator {
    /**
     * 先很慢后快
     *
     * @param t
     * @return
     */
    public static float easeInQuad(float t) {
        return adjustValue(t * t);
    }

    /**
     * 先很慢后快
     *
     * @param t
     * @return
     */
    public static float easeInCubic(float t) {
        return adjustValue(t * t * t);
    }

    /**
     * 先很慢后快
     *
     * @param t
     * @return
     */
    public static float easeInQuart(float t) {
        return adjustValue(t * t * t * t);
    }


    public static float easeInSine(float t) {
        return adjustValue((float) (1.0f - Math.cos((t * Math.PI) / 2f)));
    }

    public static float easeInOutSine(float t) {
        return adjustValue((float) (-(Math.cos(Math.PI * t) - 1.0) / 2f));
    }

    public static float easeOutCubic(float t) {
        return adjustValue((float) (1.0f - Math.pow(1.0 - t, 3.0)));
    }

    public static float easeOutQuart(float t) {
        return adjustValue((float) (1.0f - Math.pow(1.0 - t, 4.0)));
    }

    /**
     * 先非常非常慢后快
     * @param t
     * @return
     */
    public static float easeOutQuint(float t){
        return (float) (1f -  Math.pow(1f - t, 5));
    }


    static private float adjustValue(float value) {
        if (value - 1 > 0.000001) {
            value = 1.0f;
        }

        if (value < 0.0) {
            value = 0.0f;
        }

        return value;
    }


}
