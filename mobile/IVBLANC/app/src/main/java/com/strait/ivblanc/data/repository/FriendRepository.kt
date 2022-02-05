package com.strait.ivblanc.data.repository

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.FriendApi
import com.strait.ivblanc.data.model.dto.FriendForUpload
import com.strait.ivblanc.data.model.response.FriendResponse
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.StatusCode
import java.lang.Exception

class FriendRepository {
    val friendApi = ApplicationClass.sRetrofit.create(FriendApi::class.java)

    suspend fun getAllFriends(applicant:String): Resource<List<FriendResponse>> {
        return try{
            val response=friendApi.getAllFriends(applicant)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun cancelFriend(friend:FriendForUpload):Resource<FriendResponse>{
        return try{
            val response=friendApi.cancelFriend(friend)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun deleteFriend(friend:FriendForUpload):Resource<FriendResponse>{
        return try{
            val response=friendApi.deleteFriend(friend)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun getAllFriendRequest(applicant:String): Resource<List<FriendResponse>> {
        return try{
            val response=friendApi.getAllFriendRequest(applicant)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
    suspend fun getAllAcceptFriend(applicant:String): Resource<List<FriendResponse>> {
        return try{
            val response=friendApi.getAllAcceptFriend(applicant)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
    suspend fun acceptFriend(friend:FriendForUpload):Resource<FriendResponse>{
        return try{
            val response=friendApi.acceptFriend(friend)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
    suspend fun getAllNotAcceptFriend(applicant:String): Resource<List<FriendResponse>> {
        return try{
            val response=friendApi.getAllNotAcceptFriend(applicant)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
    suspend fun requestFriend(friend:FriendForUpload):Resource<FriendResponse>{
        return try{
            val response=friendApi.requestFriend(friend)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey("clothes_id")) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
}