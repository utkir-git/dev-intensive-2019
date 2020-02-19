package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = getWindow().getDecorView().findViewById(android.R.id.content) as View
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    imm.showSoftInput(view, 0)
}

fun Activity.isKeyboardOpen(): Boolean {

    val rect = Rect()

    windowManager.defaultDisplay.getRectSize(rect)
    val foolHeight = rect.height()

    window.decorView.getWindowVisibleDisplayFrame(rect)
    val statusBarHeight = rect.top
    val difference = foolHeight - statusBarHeight - rect.height()
    val minKeyboardSize = 128
    return difference > minKeyboardSize

}

fun Activity.isKeyboardClosed(): Boolean = !isKeyboardOpen()