package ru.skillbranch.devintensive.extensions

import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.devintensive.R

fun Snackbar.applyThemeColors(): Snackbar {
    val tvBack = TypedValue()
    val tvText = TypedValue()
    context.theme.resolveAttribute(R.attr.snackbarBackgroundColor, tvBack, true)
    context.theme.resolveAttribute(R.attr.snackbarTextColor, tvText, true)
    this.view.setBackgroundColor(tvBack.data)
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        .setTextColor(tvText.data)
    return this
}