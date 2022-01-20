package com.strait.ivblanc.data.repository

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.api.UserApi
import com.strait.ivblanc.data.model.dto.UserForJoin
import retrofit2.Response

class UserRepository {
    var userApi: UserApi = ApplicationClass.sRetrofit.create(UserApi::class.java)

    suspend fun join(user: UserForJoin): Response<BaseResponse> {
        return userApi.join(user)
    }
}