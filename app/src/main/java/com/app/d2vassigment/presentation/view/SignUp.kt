package com.app.d2vassigment.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.app.d2vassigment.R
import com.app.d2vassigment.databinding.ActivitySignUpBinding
import com.app.d2vassigment.domain.model.UserModel
import com.app.d2vassigment.presentation.viewmodel.UserViewModel
import com.app.d2vassigment.presentation.viewmodel.UserViewState
import com.app.d2vassigment.util.CustomToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private  var userNameType : Int = -1

    // initializing view model
    private val signUpModel : UserViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // view onClick listener
        listener()

    }

    private fun listener() {
        binding.SignUp.setOnClickListener {
            // Validating the input fields
            validateAndSignup()
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.userName.addTextChangedListener(object : TextWatcher{
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

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.isEmpty()) {
                    // If password is empty, clearing the strength text
                    binding.passwordStrength.text = ""
                } else {
                    // If password is not empty, updating UI based on password strength
                    when (getPasswordStrength(password)) {
                        PasswordStrength.WEAK -> binding.passwordStrength.text = "Weak password"
                        PasswordStrength.STRONG -> binding.passwordStrength.text = "Strong password"
                        PasswordStrength.VERY_STRONG -> binding.passwordStrength.text = "Very strong password"
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }


    // input field verification
    private fun validateAndSignup() {
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
        }else{
            // user model for registration
            val user = UserModel(
                username = binding.userName.text.toString(),
                password = binding.password.text.toString()
            )

            // Calling  method of SignUpUseCase
            signUpModel.signUp(user)

            // observing state for response from repository
            signUpModel.userState.observe(this) { state ->
                when (state) {
                    is UserViewState.Loading -> showLoading()
                    is UserViewState.Success -> showSuccess()
                    is UserViewState.Error -> showError(state.message)
                }
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
        customToast.showToast("User registered successfully !")
    }

    // showing loader
    private fun showLoading() {
        binding.loader.visibility = View.VISIBLE
    }


    // checking password strength
    fun getPasswordStrength(password: String): PasswordStrength {
        // Checking if password contains alphabets, numbers, and special characters
        val containsAlphabet = password.any { it.isLetter() }
        val containsNumber = password.any { it.isDigit() }
        val containsSpecialCharacter = password.any { it.isLetterOrDigit().not() }

        // Determining password strength
        return when {
            containsAlphabet && containsNumber && containsSpecialCharacter && password.length > 6 -> PasswordStrength.VERY_STRONG
            containsAlphabet && containsNumber || (containsAlphabet && containsSpecialCharacter) -> PasswordStrength.STRONG
            else -> PasswordStrength.WEAK
        }
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

    enum class PasswordStrength {
        WEAK,
        STRONG,
        VERY_STRONG
    }
}