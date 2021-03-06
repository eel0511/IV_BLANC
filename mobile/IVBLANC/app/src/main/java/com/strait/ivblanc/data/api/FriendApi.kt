package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.dto.FriendForUpload
import com.strait.ivblanc.data.model.response.FriendListResponse
import com.strait.ivblanc.data.model.response.FriendResponse
import okhttp3.internal.http.hasBody
import retrofit2.Response
import retrofit2.http.*

interface FriendApi {

    @GET("/api/friend/all")
    suspend fun getAllFriends(@Query("applicant")applicant:String):Response<FriendListResponse>

    @HTTP(method = "DELETE", path = "/api/friend/cancel", hasBody = true)
    suspend fun cancelFriend(@Body friend: FriendForUpload):Response<FriendResponse>

    @DELETE("/api/friend/delete")
    suspend fun deleteFriend(@Body friend: FriendForUpload):Response<FriendResponse>

    @GET("/api/friend/friendrequest")
    suspend fun getAllFriendRequest(@Query("applicant")applicant: String):Response<FriendListResponse>

    @GET("/api/friend/isaccept")
    suspend fun getAllAcceptFriend(@Query("applicant")applicant: String):Response<FriendListResponse>

    @POST("/api/friend/isaccept")
    suspend fun acceptFriend(@Body friend: FriendForUpload):Response<FriendResponse>

    @GET("/api/friend/isnotaccept")
    suspend fun getAllNotAcceptFriend(@Query("applicant")applicant: String):Response<FriendListResponse>

    @POST("/api/friend/request")
    suspend fun requestFriend(@Body friend: FriendForUpload):Response<FriendResponse>

}