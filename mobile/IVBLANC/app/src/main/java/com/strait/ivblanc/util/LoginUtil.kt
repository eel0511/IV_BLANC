package com.strait.ivblanc.util

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.model.dto.UserInfo

object LoginUtil {
    val USER_NAME = "userName"
    val USER_EMAIL = "userEmail"
    val USER_ID = "userId"

    private val preferences = ApplicationClass.sSharedPreferences

    fun isAutoLogin(): Boolean {
        return preferences.getAutoLoginFlag()
    }

    fun signOut() {
        preferences.deleteString(ApplicationClass.JWT)
    }

    fun setAutoLogin(flag: Boolean) {
        preferences.setAutoLogin(flag)
    }

    fun isTokenExisted(): Boolean {
        return !preferences.getString(ApplicationClass.JWT).isNullOrBlank()
    }

    fun saveUserInfo(userInfo: UserInfo) {
        preferences.setString(USER_NAME, userInfo.name)
        preferences.setString(USER_EMAIL, userInfo.email)
        preferences.setString(USER_ID, userInfo.userId.toString())
    }

    fun deleteUserInfo() {
        preferences.deleteString(USER_NAME)
        preferences.deleteString(USER_EMAIL)
        preferences.deleteString(USER_ID)
    }

    fun getUserInfo(): UserInfo? {
        val userEmail = preferences.getString(USER_EMAIL)
        val userName = preferences.getString(USER_NAME)
        val userId = preferences.getString(USER_ID)?.toInt()

        return if (userEmail.isNullOrBlank() or userName.isNullOrBlank() or (userId == null)) {
            null
        } else {
            UserInfo(userEmail!!, userName!!, userId!!)
        }
    }
}