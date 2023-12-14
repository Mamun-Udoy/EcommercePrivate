package com.example.ecommerce.repo

import com.example.ecommerce.network.ApiInterface
import com.example.ecommerce.network.model.UserLoginRequest
import com.example.ecommerce.network.model.UserRegistrationRequest
import javax.inject.Inject

class AppRepo @Inject constructor(
    private val api: ApiInterface
) {
    suspend fun userLogin(url: String,userDataRequest: UserLoginRequest) = api.login(url,userDataRequest)

    suspend fun userSignUp(url: String, userRegistrationRequest: UserRegistrationRequest) = api.signup(url, userRegistrationRequest)


}