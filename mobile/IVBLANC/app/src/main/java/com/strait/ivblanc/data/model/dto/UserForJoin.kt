package com.strait.ivblanc.data.model.dto

import com.google.gson.annotations.SerializedName

data class UserForJoin(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_chk")
    val passwordCheck: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("age")
    val age: Int,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("social")
    var social: Int = 0) {

}