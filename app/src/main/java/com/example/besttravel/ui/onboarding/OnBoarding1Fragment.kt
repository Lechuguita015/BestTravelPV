package com.example.besttravel.ui.onboarding

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.example.besttravel.R
import java.io.InputStream


class OnBoarding1Fragment : Fragment(R.layout.fragment_on_boarding1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnskip = view.findViewById<Button>(R.id.btn_skip)
        try {
            // obtén el archivo SVG desde la carpeta de recursos
            val inputStream: InputStream = resources.openRawResource(R.raw.onboard_1)
            // crea un objeto SVG desde el archivo
            val svg = SVG.getFromInputStream(inputStream)
            // renderiza el objeto SVG en un Picture
            val picture = svg.renderToPicture()
            // convierte el Picture en un Drawable
            val drawable: Drawable = PictureDrawable(picture)
            // establece el Drawable en un TextView
            val textView: TextView = view.findViewById(R.id.tv_imageBoard)
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        } catch (e: SVGParseException) {
            // manejar excepción
        }
        btnskip.setOnClickListener {
            findNavController().navigate(R.id.action_onBoarding1Fragment_to_login2)
        }
    }
}