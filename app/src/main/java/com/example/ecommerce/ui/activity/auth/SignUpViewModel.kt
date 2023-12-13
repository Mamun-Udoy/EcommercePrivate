package com.example.ecommerce.ui.activity.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.network.model.UserRegistrationRequest
import com.example.ecommerce.network.ApiClient
import com.example.ecommerce.repo.AppRepo
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor
    (private val appRepo: AppRepo) : ViewModel() {

    //val postUserInfo: MutableLiveData<SignUpData> = MutableLiveData()

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> = _registrationResult


    fun postUserInfo(userRegistrationRequest: UserRegistrationRequest) = viewModelScope.launch {


        Log.d("username", "print username ${userRegistrationRequest.username}")

        val url = "https://fakestoreapi.com/users"

        val userRegistrationRequest = userRegistrationRequest

        try {


        }
        catch (exp: Exception){
            Log.d("login_error", "getUserInfo: error: ${exp.localizedMessage}")
        }

        val response = appRepo.userSignUp(
            url = "https://fakestoreapi.com/users",
            userRegistrationRequest
        )

        if (response.isSuccessful) {

            _registrationResult.value = true

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

            Log.d("response_details", Gson().toJson(response.body()))


        } else {
            _registrationResult.value = false
            val code = response.code()
            val error = response.errorBody().toString()
            Log.d(
                "error_userregistration",
                "UserRegistrationInfoError: error: error code: $code, $error"
            )
        }
    }
}