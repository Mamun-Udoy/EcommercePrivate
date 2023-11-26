package com.example.ecommerce.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data_model.UserRequest
import com.example.ecommerce.data_model.UserResponse
import com.example.ecommerce.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel: ViewModel() {

    fun getUserInfo(userName: String, password: String) = viewModelScope.launch {
        val response = RetrofitInstance.api.login(
            password = password,
            userName = userName
        )
        if (response.isSuccessful) {

            val token = response.body()?.token
            Log.d("token_db", "getUserInfo: token: $token")

        } else {
            val code = response.code()
            val error = response.errorBody().toString()
            Log.d("error_retrofit", "phoneInfoList: error: error code: $code, $error")
        }
    }




}