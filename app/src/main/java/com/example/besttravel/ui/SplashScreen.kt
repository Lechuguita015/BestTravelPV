package com.example.besttravel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.besttravel.R
import com.example.besttravel.ui.onboarding.Onboardings

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // Inicia la siguiente actividad después del temporizador
            startActivity(Intent(this, Onboardings::class.java))
            // Cierra la actividad actual
            finish()
        }, 5000)
    }
}