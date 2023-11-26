package com.example.ecommerce.data_model


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("token") val token: String
)