package com.ssafy.template.config

import com.strait.ivblanc.config.ApplicationClass.Companion.X_AUTH_TOKEN
import com.strait.ivblanc.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_AUTH_TOKEN)
        if (jwtToken != null) {
            builder.addHeader("X-AUTH-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}