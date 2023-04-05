package com.example.besttravel.ui.servicesbt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.besttravel.R

class ServicesbtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicesbt)
        //Obtiene el ID del fragmento a mostrar
        val fragmentId = intent.getIntExtra("FRAGMENT_ID",-1)
        //Muestra el fragmento correspondiente sin el navigation Bottom
        val fragment = when (fragmentId){
            R.id.fragment_restaurant -> RestaurantsFragment()
            R.id.fragment_beaches -> BeachesFragment()
            R.id.fragment_hotel -> HotelsFragment()
            R.id.fragment_cars -> CarsFragment()
            else -> null
        }
        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_services,it).commit()
        }
    }
}