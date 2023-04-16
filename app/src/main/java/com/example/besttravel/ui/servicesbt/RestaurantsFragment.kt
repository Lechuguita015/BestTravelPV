package com.example.besttravel.ui.servicesbt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.besttravel.R
import com.example.besttravel.models.restaurants.RestaurantsResponse
import com.example.besttravel.databinding.FragmentRestaurantsBinding
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayRestaurantsResponseAdapter
import com.example.besttravel.ui.interfaces.ItemClickListener

class RestaurantsFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantsBinding
    private var mRestaurantsList: ArrayList<RestaurantsResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        getBestRestaurants()
        return binding.root
    }

    private fun getBestRestaurants() {
        AndroidNetworking.get("https://api-best-travel.azurewebsites.net/api/service/restaurant/all")
            .setPriority(com.androidnetworking.common.Priority.HIGH)
            .build()
            .getAsObjectList(
                RestaurantsResponse::class.java,
                object : ParsedRequestListener<ArrayList<RestaurantsResponse>> {
                    override fun onResponse(response: ArrayList<RestaurantsResponse>) {
                        Log.e("TAG", "onResponse: Best Restaurants: $response")


                        mRestaurantsList.addAll(response)
                        initRestaurantsAdapter()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: ${anError!!.message}")

                    }
                })
    }
    private fun initRestaurantsAdapter()
    {
        val adapter = DisplayRestaurantsResponseAdapter(requireContext(),mRestaurantsList,object : ItemClickListener
        {
            override fun onClick(position: Int) {
                startActivity(
                    Intent(requireContext(), PlaceDetailsActivity::class.java)
                    .putExtra("isRestaurant",true)
                    .putExtra("RestaurantItem",mRestaurantsList[position]))

            }
            override fun onFavClick(position: Int) {

            }

        })
       binding.rv.layoutManager = LinearLayoutManager(requireContext())
       binding.rv.adapter = adapter
    }

}