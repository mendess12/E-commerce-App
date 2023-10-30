package com.yusufmendes.sisterslabgraduationproject.model

data class UserResponse(
    val message: String,
    val status: Int,
    val user: User
)