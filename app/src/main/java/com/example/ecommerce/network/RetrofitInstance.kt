package com.example.ecommerce.network

import com.example.ecommerce.api.User
import com.example.ecommerce.network.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    // Create the Interceptor


    companion object {

//        val chuckerInterceptor = ChuckerInterceptor.Builder(App.)
//            .maxContentLength(250_000L)
//            .alwaysReadResponseBody(true)
//            .createShortcut(true)
//            .build()

        val retrofit by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                //.addInterceptor(chuckerInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }
        val api by lazy {
            retrofit.create(User::class.java)
        }
    }
}