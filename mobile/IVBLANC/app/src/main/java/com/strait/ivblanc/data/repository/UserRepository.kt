package com.strait.ivblanc.data.repository

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.UserApi
import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.response.JoinResponse
import com.strait.ivblanc.util.Resource
import retrofit2.Response
import java.lang.Exception

class UserRepository {
    var userApi: UserApi = ApplicationClass.sRetrofit.create(UserApi::class.java)

    suspend fun join(user: UserForJoin): Resource<JoinResponse> {
        return try {
            val response = userApi.join(user)
            if(response.isSuccessful) {
                return if(response.code() == 200) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "회원가입에 실패했습니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 연결 상태를 확인해 주세요")
        }
    }
}