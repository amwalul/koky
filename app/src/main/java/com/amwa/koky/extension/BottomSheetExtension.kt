package com.amwa.koky.extension

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun <V : View> BottomSheetBehavior<V>.onStateChanged(onStateChanged: (newState: Int) -> Unit) {
    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            onStateChanged(newState)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }

    })
}