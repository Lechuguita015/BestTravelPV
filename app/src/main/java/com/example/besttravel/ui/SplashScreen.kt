package com.example.besttravel.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.example.besttravel.R
import com.example.besttravel.ui.Home.MenuHome
import com.example.besttravel.ui.onboarding.Onboardings
import java.io.InputStream

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // Inicia la siguiente actividad después del temporizador
            startActivity(Intent(this, MenuHome::class.java))
            // Cierra la actividad actual
            finish()
        }, 2000)
    }
}