package com.strait.ivblanc.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.template.config.ReceivedCookiesInterceptor
import com.ssafy.template.config.XAccessTokenInterceptor
import com.strait.ivblanc.BuildConfig
import com.strait.ivblanc.util.SharedPreferencesUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {
    val BASE_URL = "http://119.56.162.61:8888"
    val TIME_OUT = 5000L

    companion object {

        // JWT Token Header 키 값
        const val X_AUTH_TOKEN = "X-AUTH-TOKEN"
        const val SHARED_PREFERENCES_NAME = "IV_BLANC"
        const val COOKIES_KEY_NAME = "cookies"
        const val AUTO_LOGIN = "auto_login_flag"
        const val JWT = "JWT"

        lateinit var sRetrofit: Retrofit
        lateinit var sSharedPreferences: SharedPreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = SharedPreferencesUtil(applicationContext)
        initRetrofit()
        KakaoSdk.init(this, "${BuildConfig.KAKAO_API_KEY}")
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    fun initRetrofit() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS).setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addInterceptor(AddCookiesInterceptor())  //쿠키 전송
            .addInterceptor(ReceivedCookiesInterceptor()) //쿠키 추출
            .build()

        sRetrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}