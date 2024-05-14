package com.app.d2vassigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.d2vassigment.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

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

        binding.Login.setOnClickListener {
            validateInput()
        }
    }

    // validating input fields
    private fun validateInput() {
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
        }

        Toast.makeText(this, "All input fields validated and sign in successfully !", Toast.LENGTH_SHORT).show()
    }
}