package com.akerimtay.movieapp.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.showToast(text: String?, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun Context.showToast(@StringRes text: Int, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun Fragment.showToast(text: String?, length: Int = Toast.LENGTH_LONG) {
    requireContext().showToast(text, length)
}

fun Fragment.showToast(@StringRes text: Int, length: Int = Toast.LENGTH_LONG) {
    requireContext().showToast(text, length)
}