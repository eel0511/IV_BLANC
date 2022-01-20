package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.response.EmailCheckResponse
import com.strait.ivblanc.data.model.response.JoinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @POST("/api/sign/signup")
    suspend fun join(@Body user: UserForJoin): Response<JoinResponse>

    // TODO: 2022/01/20 이메일 체크 메서드 수정 필요
    @POST("/api/sign/")
    suspend fun emailCheck(email: String): Response<EmailCheckResponse>
}