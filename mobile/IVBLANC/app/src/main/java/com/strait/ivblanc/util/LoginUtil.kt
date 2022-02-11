package com.strait.ivblanc.util

import com.strait.ivblanc.config.ApplicationClass

object LoginUtil {
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
}