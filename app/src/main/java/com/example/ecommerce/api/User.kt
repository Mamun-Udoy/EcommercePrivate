package com.example.ecommerce.api

import com.example.ecommerce.data_model.UserRequest
import com.example.ecommerce.data_model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface User {


    @FormUrlEncoded
    @POST("users")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): Response<UserResponse>


    @POST("users")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserResponse>

}