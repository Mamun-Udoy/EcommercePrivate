package com.example.ecommerce.ui.activity.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.network.model.UserLoginRequest
import com.example.ecommerce.network.ApiClient
import com.example.ecommerce.repo.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val appRepo: AppRepo
): ViewModel() {

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    fun getUserInfo(userName: String, password: String) = viewModelScope.launch {
        val url = "https://fakestoreapi.com/auth/login"
        val userLoginRequest = UserLoginRequest(username = userName, password = password)

        try {
            val response = appRepo.userLogin(url,userLoginRequest)

            if (response.isSuccessful) {

                val token = response.body()?.token?:""
                _loginResult.value = token
                Log.d("tokeen_db", "getUserInfo: token: $token")

            } else {
                val code = response.code()
                val error = response.errorBody().toString()
                Log.d("error login", "user login: error: error code: $code, $error")
            }

        } catch (exp: Exception) {
            Log.d("login_error", "getUserInfo: error: ${exp.localizedMessage}")
        }

    }




}