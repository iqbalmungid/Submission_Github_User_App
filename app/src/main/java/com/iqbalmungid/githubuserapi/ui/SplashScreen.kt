package com.iqbalmungid.githubuserapi.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.iqbalmungid.githubuserapi.R
import com.iqbalmungid.githubuserapi.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val time = 1500
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, time.toLong())
    }
}