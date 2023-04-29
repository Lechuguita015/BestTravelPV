package com.example.besttravel.ui.servicesbt

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.besttravel.R
import com.example.besttravel.databinding.FragmentCarsBinding
import com.example.besttravel.ui.home.MenuHome

class CarsFragment : Fragment() {

    private lateinit var binding: FragmentCarsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCarsBinding.inflate(layoutInflater, container, false)

        binding.tvReturn.setOnClickListener {
            val intent = Intent(activity, MenuHome::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}