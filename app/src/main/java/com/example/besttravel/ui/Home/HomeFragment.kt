package com.example.besttravel.ui.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.besttravel.R
import com.example.besttravel.ui.servicesbt.ServicesbtActivity

open class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btnRestaurant = view.findViewById<ImageButton>(R.id.iv_restaurant)
        val btnBeaches = view.findViewById<ImageButton>(R.id.iv_beaches)
        val btnHotel = view.findViewById<ImageButton>(R.id.iv_hotel)
        val btnCars = view.findViewById<ImageButton>(R.id.iv_rents_cars)

        btnRestaurant.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID",R.id.fragment_restaurant)
            startActivity(intent)
        }
        btnBeaches.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID",R.id.fragment_beaches)
            startActivity(intent)
        }
        btnHotel.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID",R.id.fragment_hotel)
            startActivity(intent)
        }
        btnCars.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID",R.id.fragment_cars)
            startActivity(intent)
        }

        return view
    }
}