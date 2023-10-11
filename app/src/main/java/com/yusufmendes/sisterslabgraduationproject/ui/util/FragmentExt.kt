package com.yusufmendes.sisterslabgraduationproject.ui.util

import androidx.fragment.app.Fragment
import com.yusufmendes.sisterslabgraduationproject.util.extensions.showSnackBar

fun Fragment.showSnackBar(message: String) {
    requireView().showSnackBar(message)
}