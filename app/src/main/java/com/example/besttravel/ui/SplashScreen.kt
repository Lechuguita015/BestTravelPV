package com.example.besttravel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.besttravel.R
import com.example.besttravel.ui.home.MenuHome
import com.example.besttravel.ui.onboarding.Onboardings
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // Inicia la siguiente actividad después del temporizador
            if (FirebaseAuth.getInstance().currentUser != null) {
                val intent = Intent(this, MenuHome::class.java)
                startActivity(intent)
            } else {
                startActivity(Intent(this, Onboardings::class.java))
            }
            // Cierra la actividad actual
            finish()
        }, 3500)
    }
    //api-best-travel.azurewebsites.net/api/service/beach/all
    //api-best-travel.azurewebsites.net/api/service/hotel/all
    //api-best-travel.azurewebsites.net/api/service/restaurant/all
}