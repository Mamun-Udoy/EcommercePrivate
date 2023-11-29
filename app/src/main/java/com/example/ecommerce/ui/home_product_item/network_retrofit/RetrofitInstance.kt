package com.example.ecommerce.ui.home_product_item.network_retrofit

import com.example.ecommerce.ui.home_product_item.api.PhoneItemApi
import com.example.ecommerce.ui.home_product_item.network_retrofit.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        val retrofit by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()

        }
        val api by lazy {
            retrofit.create(PhoneItemApi::class.java)
        }
    }
}