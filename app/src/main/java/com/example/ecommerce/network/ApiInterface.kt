package com.example.ecommerce.network

import com.example.ecommerce.network.model.UserLoginRequest
import com.example.ecommerce.network.model.UserRegistrationInfoResponse
import com.example.ecommerce.network.model.UserRegistrationRequest
import com.example.ecommerce.network.model.UserLoginResponse
import com.example.ecommerce.network.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("/products")
    suspend fun getProductData(
        @Query("limit") limit: Int = 0,
    ): Response<ProductsResponse>

    @GET("/products/category/{category_name}")
    suspend fun getCategoryProductData(
        @Path("category_name") categoryName: String
    ): Response<ProductsResponse>

    @POST
    suspend fun login(
        @Url url:String?,
        @Body userLoginRequest: UserLoginRequest
    ): Response<UserLoginResponse>

    @POST
    suspend fun signup(
        @Url url:String?,
        @Body userRegistrationRequest: UserRegistrationRequest
    ): Response<UserRegistrationInfoResponse>
}