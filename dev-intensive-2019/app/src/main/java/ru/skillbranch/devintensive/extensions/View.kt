package ru.skillbranch.devintensive.extensions

import android.view.View

fun View.show(predicate: () -> Boolean) {
    this.visibility = if (predicate()) View.VISIBLE else View.GONE
}