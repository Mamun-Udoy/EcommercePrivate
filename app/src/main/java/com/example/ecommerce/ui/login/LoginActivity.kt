package com.example.ecommerce.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.ecommerce.MainActivity
import com.example.ecommerce.databinding.ActivityLoginBinding
import com.example.ecommerce.ui.signup.SignUpActivity
import com.example.ecommerce.view_model.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {

            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {

            val userEmail = binding.useremail.text.toString()
            val userPassword = binding.userpassword.text.toString()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }


}