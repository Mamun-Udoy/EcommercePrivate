package com.example.ecommerce.api

import com.example.ecommerce.data_model.UserLoginRequest
import com.example.ecommerce.data_model.UserRegistrationInfoResponse
import com.example.ecommerce.data_model.UserRequest
import com.example.ecommerce.data_model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface User {


//    @FormUrlEncoded // when we are using field we have to give this annotation to create form
//    @POST("auth/login")
//    suspend fun login(
//        @Field("username") userName: String,
//        @Field("password") password: String,
//    ): Response<UserResponse>

    @POST("auth/login")
    suspend fun login(@Body userLoginRequest: UserLoginRequest): Response<UserResponse>


    @POST("users")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserRegistrationInfoResponse>

}