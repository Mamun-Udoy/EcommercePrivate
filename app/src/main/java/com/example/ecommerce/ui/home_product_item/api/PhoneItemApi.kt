package com.example.ecommerce.ui.home_product_item.api

import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import com.example.ecommerce.ui.home_product_item.product_data_model.ProductDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhoneItemApi {

    @GET("/products")
    suspend fun getProductData(
        @Query("limit") limit: Int = 0,
    ): Response<RetrofitDataModel>

    @GET("/products/category/{category_name}")
    suspend fun getCategoryProductData(
        @Path("category_name") categoryName: String
    ): Response<RetrofitDataModel>
}