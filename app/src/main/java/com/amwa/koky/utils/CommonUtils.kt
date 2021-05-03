package com.amwa.koky.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object CommonUtils {

    fun showKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view.findFocus(), 0)
    }
}