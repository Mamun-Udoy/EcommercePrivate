package com.example.ecommerce.ui.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data_model.UserRequest
import com.example.ecommerce.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val postUserInfo: MutableLiveData<SignUpData> = MutableLiveData()


    fun postUserInfo(signUpData: SignUpData) = viewModelScope.launch {

        val userRequest = UserRequest(
            address = UserRequest.Address(
                city = signUpData.city,
                geolocation = UserRequest.Address.Geolocation(
                    lat = signUpData.latitude,
                    long = signUpData.longitude
                ),
                number = signUpData.number.toIntOrNull(),
                street = signUpData.street,
                zipcode = signUpData.zipCode
            ),
            email = signUpData.email,
            name = UserRequest.Name(
                firstname = signUpData.firstName,
                lastname = signUpData.lastName
            ),
            password = signUpData.password,
            phone = signUpData.phoneNumber,
            username = signUpData.username


        )

        Log.d("username", "print username ${userRequest.username}")

        val response = RetrofitInstance.api.signup(userRequest)

        if (response.isSuccessful) {

            val userName = response.body()?.username.toString()
            val userEmail = response.body()?.email
            val userPassword = response.body()?.password
            val city = response.body()?.address?.city
            val number = response.body()?.address?.number
            val street = response.body()?.address?.street
            val zipcode = response.body()?.address?.zipcode
            val latitude = response.body()?.address?.geolocation?.lat
            val longitude = response.body()?.address?.geolocation?.long
            val firstName = response.body()?.name?.firstname
            val lastName = response.body()?.name?.lastname
            val phonenumber = response.body()?.phone

            Log.d(
                "successfully_userregistration",
                "userRegsitrationInfo: $userName, $userEmail, $userPassword, $city, $number,$street, $zipcode,$latitude,$longitude"
            )

            Log.d("response_body", "Response body: ${Gson().toJson(response.body())}")

            Log.d("response_details",  Gson().toJson(response.body()))




        } else {
            val code = response.code()
            val error = response.errorBody().toString()
            Log.d(
                "error_userregistration",
                "UserRegistrationInfoError: error: error code: $code, $error"
            )
        }
    }
}