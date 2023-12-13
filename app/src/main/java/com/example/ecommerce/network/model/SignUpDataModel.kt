package com.example.ecommerce.network.model

data class SignUpDataModel(
    val email: String,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val city: String,
    val street: String,
    val number: String,
    val zipCode: String,
    val latitude: String,
    val longitude: String,
    val phoneNumber: String
)


