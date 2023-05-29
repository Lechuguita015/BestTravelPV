package com.example.besttravel.ui.startapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.besttravel.R
import com.example.besttravel.ui.onboarding.Onboardings


class SuccessfulFragment : Fragment(R.layout.fragment_successful) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        Handler().postDelayed({
            // Inicia la siguiente actividad después del temporizador
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            // Cierra la actividad actual
            requireActivity().finish()
        }, 60000)
        val exit = view.findViewById<TextView>(R.id.exit)
        exit.setOnClickListener {
            findNavController().navigate(R.id.action_successfulFragment_to_loginFragment)
        }
    }
}