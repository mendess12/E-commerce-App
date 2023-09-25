package com.yusufmendes.sisterslabgraduationproject.util.extensions

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message:String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackBar.setTextColor(Color.WHITE)
    snackBar.setBackgroundTint(Color.RED)
    snackBar.show()
}