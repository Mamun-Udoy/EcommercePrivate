package com.example.ecommerce.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data_model.UserLoginRequest
import com.example.ecommerce.data_model.UserRequest
import com.example.ecommerce.data_model.UserResponse
import com.example.ecommerce.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel: ViewModel() {

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    fun getUserInfo(userName: String, password: String) = viewModelScope.launch {
        val userLoginRequest = UserLoginRequest(username = userName, password = password)
        val response = RetrofitInstance.api.login(userLoginRequest)
        if (response.isSuccessful) {

            val token = response.body()?.token?:""
            _loginResult.value = token
            Log.d("token_db", "getUserInfo: token: $token")

        } else {
            val code = response.code()
            val error = response.errorBody().toString()
            Log.d("error login", "user login: error: error code: $code, $error")
        }
    }




}