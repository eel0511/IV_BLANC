package com.strait.ivblanc.dto

data class UserForJoin(val email: String, val password: String , val name: String, val gender: Int, val phoneNumber: String, var social: Int = 0) {

}