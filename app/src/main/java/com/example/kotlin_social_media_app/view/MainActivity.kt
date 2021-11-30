package com.example.kotlin_social_media_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val signInIntent = Intent(this, BottomNavActivity::class.java)
            startActivity(signInIntent)
            finish()
        }, 1000)
    }
}