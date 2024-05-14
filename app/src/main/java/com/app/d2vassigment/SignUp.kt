package com.app.d2vassigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.app.d2vassigment.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding

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

    private fun validateAndSignup() {

        if (binding.name.text.trim().isEmpty()) {
            binding.name.requestFocus()
            binding.name.error = "Please enter your name"
            return
        } else if (!binding.name.text.matches(Regex("[A-Za-z ]+"))) {
            binding.name.requestFocus()
            binding.name.error = "Name should contain only letters and spaces"
            return
        }


        if (binding.email.text.trim().isEmpty()) {
            binding.email.requestFocus()
            binding.email.error = "Please enter your email"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text.trim()).matches()) {
            binding.email.requestFocus()
            binding.email.error = "Please enter a valid email address"
            return
        }

        if (binding.password.text.trim().isEmpty()) {
            binding.password.requestFocus()
            binding.password.error = "Please enter your password"
            return
        }else if (binding.password.text.trim().length < 4){
            binding.password.requestFocus()
            binding.password.error = "Very small password"
            return
        }

        Toast.makeText(this, "All input fields validated and sign up successfully !", Toast.LENGTH_SHORT).show()

    }


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

    enum class PasswordStrength {
        WEAK,
        STRONG,
        VERY_STRONG
    }
}