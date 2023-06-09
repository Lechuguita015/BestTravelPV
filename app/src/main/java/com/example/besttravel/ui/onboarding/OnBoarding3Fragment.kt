package com.example.besttravel.ui.onboarding

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.example.besttravel.R
import com.example.besttravel.ui.startapp.Login
import java.io.InputStream

class OnBoarding3Fragment : Fragment(R.layout.fragment_on_boarding3) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val btnStart = view.findViewById<Button>(R.id.btn_start)
        try {
            // obtén el archivo SVG desde la carpeta de recursos
            val inputStream: InputStream = resources.openRawResource(R.raw.onboard_3)
            // crea un objeto SVG desde el archivo
            val svg = SVG.getFromInputStream(inputStream)
            // renderiza el objeto SVG en un Picture
            val picture = svg.renderToPicture()
            // convierte el Picture en un Drawable
            val drawable: Drawable = PictureDrawable(picture)
            // establece el Drawable en un TextView
            val textView: TextView = view.findViewById(R.id.tv_imageBoard3)
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        } catch (e: SVGParseException) {
            // manejar excepción
        }
        btnStart.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }
    }
}