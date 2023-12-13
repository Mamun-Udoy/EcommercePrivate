package com.example.ecommerce.network.model


import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("token") val token: String
)