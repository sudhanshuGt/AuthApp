package com.app.d2vassigment.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.app.d2vassigment.R
import com.app.d2vassigment.databinding.ActivityLoginBinding
import com.app.d2vassigment.domain.model.UserModel
import com.app.d2vassigment.presentation.viewmodel.UserViewModel
import com.app.d2vassigment.presentation.viewmodel.UserViewState
import com.app.d2vassigment.util.CustomToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private  var userNameType : Int = -1

    // initializing view model
    private val signUpModel : UserViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // view onClick listener
        listener()

    }

    private fun listener() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.userName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val userName = s.toString()
                if (userName.isEmpty()){
                    binding.uIc.setImageResource(R.drawable.m_m)
                }else{
                    checkUserNameType(userName)
                }
            }

        })

        binding.Login.setOnClickListener {
            validateInput()
        }
    }

    // validating input fields
    private fun validateInput() {
        if (binding.userName.text.trim().isEmpty()) {
            binding.userName.requestFocus()
            binding.userName.error = "Please enter your username"
            return
        }else if (userNameType == 1 && !Patterns.EMAIL_ADDRESS.matcher(binding.userName.text.trim()).matches()){
            binding.userName.requestFocus()
            binding.userName.error = "Please enter a valid email address"
            return
        }else if (userNameType == 0 && !isValidMobileNumber(binding.userName.text.trim().toString())){
            binding.userName.requestFocus()
            binding.userName.error = "Please enter a valid mobile number"
            return
        }else if (binding.password.text.trim().isEmpty()) {
            binding.password.requestFocus()
            binding.password.error = "Please enter your password"
            return
        }else if (binding.password.text.trim().length < 4){
            binding.password.requestFocus()
            binding.password.error = "Very small password"
            return
        }

        verifyCredAndLogin()

    }

    private fun verifyCredAndLogin() {
        // user model for registration
        val user = UserModel(
            username = binding.userName.text.toString(),
            password = binding.password.text.toString()
        )

        // Calling  method of SignUpUseCase
        signUpModel.login(user)

        // observing state for response from repository
        signUpModel.userState.observe(this) { state ->
            when (state) {
                is UserViewState.Loading -> showLoading()
                is UserViewState.Success -> showSuccess()
                is UserViewState.Error -> showError(state.message)
            }
        }
    }


    // handling error
    private fun showError(message: String) {
        binding.loader.visibility = View.GONE
        val customToast = CustomToast(this)
        customToast.showToast(message)
    }


    // showing message on successfully registration
    private fun showSuccess() {
        binding.loader.visibility = View.GONE
        val customToast = CustomToast(this)
        customToast.showToast("User logged in successfully !")
    }

    // showing loader
    private fun showLoading() {
        binding.loader.visibility = View.VISIBLE
    }


    fun checkUserNameType(input: String){
        if (input.all { it.isDigit() }) {
            userNameType = 0
            binding.uIc.setImageResource(R.drawable.mobile)
        } else {
            userNameType = 1
            binding.uIc.setImageResource(R.drawable.mail)
        }
    }

    private fun isValidMobileNumber(number: String): Boolean {
        // Regular expression to match the mobile number
        val regex = Regex("^[9876]\\d{9}$")
        return regex.matches(number)
    }
}