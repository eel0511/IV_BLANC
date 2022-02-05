package com.strait.ivblanc.data.api

import com.kakao.sdk.talk.model.Friend
import com.strait.ivblanc.data.model.dto.FriendForUpload
import com.strait.ivblanc.data.model.response.FriendResponse
import retrofit2.Response
import retrofit2.http.*

interface FriendApi {

    @GET("/api/friend/all")
    suspend fun getAllFriends(@Query("applicant")applicant:String):Response<FriendResponse>

    @DELETE("/api/friend/cancel")
    suspend fun cancelFriend(@Body friend: FriendForUpload):Response<FriendResponse>

    @DELETE("/api/freind/delete")
    suspend fun deleteFriend(@Body friend: FriendForUpload):Response<FriendResponse>

    @GET("/api/friend/friendrequest")
    suspend fun getAllFriendRequest(@Query("applicant")applicant: String):Response<FriendResponse>

    @GET("/api/friend/isaccept")
    suspend fun getAllAcceptFriend(@Query("applicant")applicant: String):Response<FriendResponse>

    @POST("/api/friend/isaccept")
    suspend fun acceptFriend(@Body friend: FriendForUpload):Response<FriendResponse>

    @GET("/api/friend/isnotaccept")
    suspend fun getAllNotAcceptFriend(@Query("applicant")applicant: String):Response<FriendResponse>

    @POST("/api/friend/request")
    suspend fun requestFriend(@Body friend: FriendForUpload):Response<FriendResponse>

}