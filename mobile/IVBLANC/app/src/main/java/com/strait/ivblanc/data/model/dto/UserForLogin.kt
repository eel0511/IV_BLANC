package com.strait.ivblanc.data.model.dto

import com.google.gson.annotations.SerializedName

data class UserForLogin(
    @SerializedName("email")
    private val email: String,
    @SerializedName("pw")
    private val password: String,
    @SerializedName("social")
    private val social: Int = 1,
    @SerializedName("token_fcm")
    private val fcmToken: String? = null
)
