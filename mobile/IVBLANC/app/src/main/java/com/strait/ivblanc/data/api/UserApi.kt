package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.response.EmailCheckResponse
import com.strait.ivblanc.data.model.response.JoinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @POST("/api/sign/signup")
    suspend fun join(@Body user: UserForJoin): Response<JoinResponse>

    @GET("/api/sign/checkEmail")
    suspend fun emailCheck(@Query("email") email: String): Response<EmailCheckResponse>

}