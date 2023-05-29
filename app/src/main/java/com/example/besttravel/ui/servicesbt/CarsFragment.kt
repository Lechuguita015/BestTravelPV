package com.example.besttravel.ui.servicesbt

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        setHasOptionsMenu(true)
        // Cambiar el título del Action Bar
        (activity as AppCompatActivity).supportActionBar?.title = "Cars"
        // Habilitar el botón de retroceso en el Action Bar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Establecer el icono del botón de retroceso
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
        return binding.root
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}