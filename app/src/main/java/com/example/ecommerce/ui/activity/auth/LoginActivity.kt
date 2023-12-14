package com.example.ecommerce.ui.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ecommerce.databinding.ActivityLoginBinding
import com.example.ecommerce.utils.SharedPreferencesManager
import com.example.ecommerce.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val pref by lazy { SharedPreferencesManager(this) } //here i am creating an object of sharedpreferencemanager to access it

    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (pref.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.signUp.setOnClickListener {

                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            var rememberMe = false
            binding.rememberMeCheckbox.setOnClickListener {
                rememberMe = true
            }

            binding.login.setOnClickListener {


                val userName = binding.userName.text.toString()
                val userPassword = binding.userpassword.text.toString()

                val validationResult = validateCredentials(userName, userPassword)

                if (validationResult.first) {

                    viewModel.getUserInfo(userName, userPassword)
                    viewModel.loginResult.observe(this) { token ->
                        if (!token.isNullOrEmpty()) {
                            if (rememberMe)
                                pref.setLoggedIn()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {

                            Toast.makeText(this, "User is Invalid", Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    val errorMessage = validationResult.second
                    Toast.makeText(this, "$errorMessage", Toast.LENGTH_SHORT).show()
                }


            }
        }


    }

    private fun validateCredentials(
        userName: String,
        userPassword: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            result = Pair(false, " Please Provide information Properly ")
        } else if (userPassword.length < 6) {
            result = Pair(false, "Please Give the right password")
        }
        return result
    }


}