package com.strait.ivblanc.config

import android.app.Application
import com.strait.ivblanc.util.SharedPreferencesUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {
    val BASE_URL = "http://119.56.162.61:9999"

    companion object {

        // JWT Token Header 키 값
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
        const val SHARED_PREFERENCES_NAME = "IV_BLANC"
        const val COOKIES_KEY_NAME = "cookies"
        const val AUTO_LOGIN = "auto_login_flag"

        lateinit var sRetrofit: Retrofit
        lateinit var sSharedPreferencesUtil: SharedPreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferencesUtil = SharedPreferencesUtil(applicationContext)
        initRetrofit()
    }

    fun initRetrofit() {

        sRetrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}