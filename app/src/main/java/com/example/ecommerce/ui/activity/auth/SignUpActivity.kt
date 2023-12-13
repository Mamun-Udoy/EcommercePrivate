package com.example.ecommerce.ui.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ecommerce.network.model.UserRegistrationRequest
import com.example.ecommerce.databinding.ActivitySignUpBinding
import com.example.ecommerce.network.model.SignUpDataModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {

            val userInfo = getUserInfoFromViews()

            val validationResult = validateCredentials(userInfo.username, userInfo.email, userInfo.password)

            if(validationResult.first){

                viewModel.postUserInfo(getUserRequest(userInfo))

                viewModel.registrationResult.observe(this) { isSuccess ->
                    if (isSuccess) {
                        // Registration successful, navigate to login activity
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(
                            this,
                            "Registration successful. Please log in.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Registration failed, handle accordingly
                        Toast.makeText(
                            this,
                            "Registration failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
            else {
                val errorMessage = validationResult.second
                Toast.makeText(this,"$errorMessage",Toast.LENGTH_SHORT).show()
            }

        }

        binding.signIn.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUserRequest(signUpDataModel: SignUpDataModel): UserRegistrationRequest {
        return UserRegistrationRequest(
            address = UserRegistrationRequest.Address(
                city = signUpDataModel.city,
                geolocation = UserRegistrationRequest.Address.Geolocation(
                    lat = signUpDataModel.latitude,
                    long = signUpDataModel.longitude
                ),
                number = signUpDataModel.number.toIntOrNull(),
                street = signUpDataModel.street,
                zipcode = signUpDataModel.zipCode
            ),
            email = signUpDataModel.email,
            name = UserRegistrationRequest.Name(
                firstname = signUpDataModel.firstName,
                lastname = signUpDataModel.lastName
            ),
            password = signUpDataModel.password,
            phone = signUpDataModel.phoneNumber,
            username = signUpDataModel.username


        )
    }

    private fun getUserInfoFromViews(): SignUpDataModel {

        return SignUpDataModel(
            email = binding.userEmail.text.toString(),
            username = binding.userName.text.toString(),
            password = binding.userpassword.text.toString(),
            firstName = binding.firstname.text.toString(),
            lastName = binding.lastname.text.toString(),
            city = binding.city.text.toString(),
            street = binding.street.text.toString(),
            number = binding.number.text.toString(),
            zipCode = binding.zipcode.text.toString(),
            latitude = binding.latitude.text.toString(),
            longitude = binding.longitude.text.toString(),
            phoneNumber = binding.userphonenumber.text.toString()
        )

    }

    private fun validateCredentials(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(
                userPassword
            )
        ) {
            result = Pair(false, " Please Provide information Properly ")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            result = Pair(false, "Please Provide valid Email Address ")

        } else if (userPassword.length < 6) {
            result = Pair(false, "Please Give Password Atleast 6 character")
        }
        return result
    }
}