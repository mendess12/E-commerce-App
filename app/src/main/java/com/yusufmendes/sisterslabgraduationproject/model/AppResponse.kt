package com.yusufmendes.sisterslabgraduationproject.model

import com.google.gson.annotations.SerializedName

open class AppResponse(
    @SerializedName("status")
    open val status: Int? = null,
    @SerializedName("message")
    open val message: String? = null
) {
    open val isSuccess: Boolean
        get() = status in 200..299

    override fun toString(): String {
        return buildString {
            append("status: $status, message: $message")
        }
    }
}