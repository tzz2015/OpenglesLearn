package com.example.opengleslearn.animation

interface IAnimation {

    fun setAlpha(alpha: Float)

    fun setProgress(alpha: Float)

    fun easeProgress(progress: Float, speedUp: Boolean): Float
}