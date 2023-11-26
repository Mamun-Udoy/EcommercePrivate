package com.example.ecommerce.data_model


import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("address")
    val address: Address?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: Name?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("username")
    val username: String?
) {
    data class Address(
        @SerializedName("city")
        val city: String?,
        @SerializedName("geolocation")
        val geolocation: Geolocation?,
        @SerializedName("number")
        val number: Int?,
        @SerializedName("street")
        val street: String?,
        @SerializedName("zipcode")
        val zipcode: String?
    ) {
        data class Geolocation(
            @SerializedName("lat")
            val lat: String?,
            @SerializedName("long")
            val long: String?
        )
    }

    data class Name(
        @SerializedName("firstname")
        val firstname: String?,
        @SerializedName("lastname")
        val lastname: String?
    )
}