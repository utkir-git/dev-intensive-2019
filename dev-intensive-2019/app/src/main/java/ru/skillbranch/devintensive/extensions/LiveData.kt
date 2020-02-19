package ru.skillbranch.devintensive.extensions

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(defaultValue: T?) = MutableLiveData<T>().also {
    if (defaultValue != null) {
        it.value = defaultValue
    }
}
