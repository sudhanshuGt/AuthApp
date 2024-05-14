package com.app.d2vassigment

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.d2vassigment.databinding.ActivityOnboardingBinding
import com.bumptech.glide.Glide

class OnBoarding : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // view onClick listener
        listener()
    }

    private fun listener() {

        binding.SignUp.setOnClickListener {
            goToSignUp()
        }
        binding.Login.setOnClickListener {
            goToLogin()
        }
    }

    // navigate to sign up activity
    private fun goToSignUp(){
        val intent = Intent(this, SignUp::class.java)
        val pairs = mutableListOf<android.util.Pair<View, String>>()

        pairs.add(android.util.Pair(binding.SignUp, "BtnTransition"))
        pairs.add(android.util.Pair(binding.Login, "BtnTransition"))
        pairs.add(android.util.Pair(binding.title, "titleTransition"))


        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.toTypedArray())
        startActivity(intent, activityOptions.toBundle())


    }


    // navigate to sign in activity
    private fun goToLogin(){
        val intent = Intent(this, Login::class.java)
        val pairs = mutableListOf<android.util.Pair<View, String>>()
        pairs.add(android.util.Pair(binding.title, "titleTransition"))


        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.toTypedArray())
        startActivity(intent, activityOptions.toBundle())
    }


}