package com.strait.ivblanc.data.repository

import android.content.res.Resources
import androidx.core.content.ContextCompat
import com.strait.ivblanc.R
import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.UserApi
import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.dto.UserForLogin
import com.strait.ivblanc.data.model.response.EmailCheckResponse
import com.strait.ivblanc.data.model.response.JoinResponse
import com.strait.ivblanc.data.model.response.LoginResponse
import com.strait.ivblanc.util.Resource
import java.lang.Exception

class UserRepository {
    var userApi: UserApi = ApplicationClass.sRetrofit.create(UserApi::class.java)

    suspend fun join(user: UserForJoin): Resource<JoinResponse> {
        return try {
            val response = userApi.join(user)
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, response.body()!!.message!!)
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "서버와 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
        }
    }

    suspend fun emailCheck(email: String): Resource<EmailCheckResponse> {
        return try {
            val response = userApi.emailCheck(email)
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, response.body()!!.message!!)
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "서버와 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
        }
    }

    suspend fun emailLogin(user: UserForLogin): Resource<LoginResponse> {
        return try {
            val response = userApi.emailLogin(user)
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, response.body()!!.message!!)
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "서버와 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
        }
    }
}