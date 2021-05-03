package com.amwa.koky.extension

import android.view.animation.Animation

fun Animation.onAnimationStart(onAnimationStart: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
            onAnimationStart()
        }

        override fun onAnimationEnd(p0: Animation?) {

        }

        override fun onAnimationRepeat(p0: Animation?) {

        }
    })
}

fun Animation.onAnimationEnd(onAnimationEnd: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            onAnimationEnd()
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }
    })
}