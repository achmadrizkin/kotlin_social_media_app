package com.example.kotlin_social_media_app.view.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.view.auth.SignInActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
            finish()
        }, 1000)
    }
}