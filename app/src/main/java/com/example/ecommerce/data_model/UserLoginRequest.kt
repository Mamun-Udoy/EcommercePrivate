package com.example.ecommerce.data_model

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
