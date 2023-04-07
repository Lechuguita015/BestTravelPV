package com.example.besttravel.ui.startapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.besttravel.R
import com.example.besttravel.ui.onboarding.Onboardings


class SuccessfulFragment : Fragment(R.layout.fragment_successful) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            // Inicia la siguiente actividad después del temporizador
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            // Cierra la actividad actual
            requireActivity().finish()
        }, 2000)
    }
}