package com.example.besttravel.ui.startapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.besttravel.R
import com.example.besttravel.databinding.FragmentErrorBinding


class ErrorFragment : Fragment(R.layout.fragment_error) {

    private lateinit var binding: FragmentErrorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorBinding.inflate(inflater,container,false)

        binding.btTryagain.setOnClickListener {
            findNavController().navigate(R.id.action_errorFragment_to_registerFragment)
        }
        // Ocultar la barra de acción
        // Ocultar la barra de acción
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return binding.root
    }
}