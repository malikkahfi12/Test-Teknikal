package com.learetechno.newsapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.learetechno.newsapp.R
import com.learetechno.newsapp.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
            }
        }, 3000)
    }
}
