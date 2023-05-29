package com.example.besttravel.ui.servicesbt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.besttravel.R
import com.example.besttravel.models.restaurants.RestaurantsResponse
import com.example.besttravel.databinding.FragmentRestaurantsBinding
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayRestaurantsResponseAdapter
import com.example.besttravel.ui.home.MenuHome
import com.example.besttravel.ui.interfaces.ApiService
import com.example.besttravel.ui.interfaces.ItemClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantsBinding
    private var mRestaurantsList: ArrayList<RestaurantsResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        initRestaurantsAdapter()
        getBestRestaurants()
        Handler(Looper.getMainLooper()).postDelayed({
            showDataReciclerView()
        }, 3500)
        setHasOptionsMenu(true)
        // Cambiar el título del Action Bar
        (activity as AppCompatActivity).supportActionBar?.title = "Restaurants"
        // Habilitar el botón de retroceso en el Action Bar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Establecer el icono del botón de retroceso
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)

        return binding.root
    }

    private fun getBestRestaurants() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-best-travel.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val restaurantService = retrofit.create(ApiService::class.java)

            restaurantService.getAllRestaurants().enqueue(object : Callback<List<RestaurantsResponse>> {
                override fun onResponse(call: Call<List<RestaurantsResponse>>, response: Response<List<RestaurantsResponse>>) {
                    if (response.isSuccessful) {
                        val restaurants = response.body()
                        Log.e("TAG", "onResponse: Best Restaurants: $restaurants")
                        mRestaurantsList.addAll(restaurants!!)
                    } else {
                        Log.e("TAG", "onError: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<RestaurantsResponse>>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }
            })
        }
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
    private fun showDataReciclerView() {
        binding.viewLoading.isVisible = false
        binding.rv.isVisible = true
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