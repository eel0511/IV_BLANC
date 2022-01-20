package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.response.JoinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/api/sign/signup")
    suspend fun join(@Body user: UserForJoin): Response<JoinResponse>
}