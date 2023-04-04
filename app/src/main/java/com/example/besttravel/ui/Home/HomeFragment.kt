package com.example.besttravel.ui.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.besttravel.R
import com.example.besttravel.ui.servicesbt.BeachesFragment
import com.example.besttravel.ui.servicesbt.CarsFragment
import com.example.besttravel.ui.servicesbt.HotelsFragment
import com.example.besttravel.ui.servicesbt.RestaurantsFragment

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val button1 = view.findViewById<ImageButton>(R.id.iv_restaurant)
        val button2 = view.findViewById<ImageButton>(R.id.iv_beaches)
        val button3 = view.findViewById<ImageButton>(R.id.iv_hotel)
        val button4 = view.findViewById<ImageButton>(R.id.iv_rents_cars)

        button1.setOnClickListener {
            val fragment = RestaurantsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        button2.setOnClickListener {
            val fragment = BeachesFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        button3.setOnClickListener {
            val fragment = HotelsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        button4.setOnClickListener {
            val fragment = CarsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return view
    }
}