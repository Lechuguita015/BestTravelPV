package com.example.besttravel.ui.onboarding

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.example.besttravel.R
import com.example.besttravel.ui.startapp.Login
import java.io.InputStream


class OnBoarding2Fragment : Fragment(R.layout.fragment_on_boarding2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnskip = view.findViewById<Button>(R.id.btn_skip)
        val btnNext = view.findViewById<Button>(R.id.btn_next)
        try {
            // obtén el archivo SVG desde la carpeta de recursos
            val inputStream: InputStream = resources.openRawResource(R.raw.onboard_2)
            // crea un objeto SVG desde el archivo
            val svg = SVG.getFromInputStream(inputStream)
            // renderiza el objeto SVG en un Picture
            val picture = svg.renderToPicture()
            // convierte el Picture en un Drawable
            val drawable: Drawable = PictureDrawable(picture)
            // establece el Drawable en un TextView
            val textView: TextView = view.findViewById(R.id.tv_imageBoard2)
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        } catch (e: SVGParseException) {
            // manejar excepción
        }
        btnskip.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoarding2Fragment_to_onBoarding3Fragment)
        }
    }
}