package com.yusufmendes.sisterslabgraduationproject.util.extensions

fun String?.orDefault(default: String): String {
    return this ?: default
}