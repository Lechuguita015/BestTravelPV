package com.example.besttravel.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.besttravel.R
import com.example.besttravel.databinding.FragmentHomeBinding
import com.example.besttravel.models.FavoriteModel
import com.example.besttravel.models.beaches.BeachesResponse
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.models.restaurants.RestaurantsResponse
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayBeachesResponseAdapter
import com.example.besttravel.ui.adapters.DisplayHotelsResponseAdapter
import com.example.besttravel.ui.adapters.DisplayRestaurantsResponseAdapter
import com.example.besttravel.ui.interfaces.ApiService
import com.example.besttravel.ui.interfaces.ItemClickListener
import com.example.besttravel.ui.servicesbt.ServicesbtActivity
import com.example.besttravel.utils.AppPrefs
import com.example.besttravel.utils.Constants
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class HomeFragment : Fragment() {

    private var mHotelsList: ArrayList<HotelsResponse> = ArrayList()
    private var mRestaurantsList: ArrayList<RestaurantsResponse> = ArrayList()
    private var mBeachesList: ArrayList<BeachesResponse> = ArrayList()
    private lateinit var rvHotels: RecyclerView
    private lateinit var rvRestaurants: RecyclerView
    private lateinit var rvBeaches: RecyclerView
    lateinit var restaurantsResponseAdapter: DisplayRestaurantsResponseAdapter
    lateinit var hotelsResponseAdapter: DisplayHotelsResponseAdapter
    lateinit var beachesResponseAdapter: DisplayBeachesResponseAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

       // val btnRestaurant = view.findViewById<CardView>(R.id.iv_restaurant)
        //val btnBeaches = view.findViewById<CardView>(R.id.iv_beaches)
        //val btnHotel = view.findViewById<CardView>(R.id.iv_hotel)
        //val btnCars = view.findViewById<CardView>(R.id.iv_rents_cars)
        //initialize all RecyclerView
        //rvHotels = view.findViewById<RecyclerView>(R.id.rv_hotels)
        //rvRestaurants = view.findViewById<RecyclerView>(R.id.rv_restaurant)
        //rvBeaches = view.findViewById<RecyclerView>(R.id.rv_beaches)


        binding.ivRestaurant.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_restaurant)
            startActivity(intent)
        }
        binding.ivBeaches.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_beaches)
            startActivity(intent)
        }
        binding.ivHotel.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_hotel)
            startActivity(intent)
        }
        binding.ivRentsCars.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_cars)
            startActivity(intent)
        }

        initHotelAdapter()
        initRestaurantsAdapter()
        initBeachesAdapter()
        if (mHotelsList.isEmpty())
        {
            getBestHotels()
        }
        if (mRestaurantsList.isEmpty())
        {
            getBestRestaurants()

        }
        if (mBeachesList.isEmpty())
        {
            getBestBeaches()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            showDataReciclerViewBeaches()
            showDataReciclerViewRestaurants()
            showDataReciclerViewHotels()

        }, 3500)
        binding.tvChargerHotels.setOnClickListener {
            if (mHotelsList.isEmpty())
            {
                getBestHotels()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                showDataReciclerViewHotels()

            }, 3500)
        }
        binding.tvChargerRestaurants.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                showDataReciclerViewRestaurants()

            }, 3500)
            if (mRestaurantsList.isEmpty())
            {
                getBestRestaurants()
            }
        }
        binding.tvChargerBeaches.setOnClickListener {
            binding.tvChargerRestaurants.isVisible = false
            if (mBeachesList.isEmpty())
            {
                getBestBeaches()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                showDataReciclerViewBeaches()

            }, 3500)
        }
        return binding.root
    }

    private fun getBestHotels() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-best-travel.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val hotelService = retrofit.create(ApiService::class.java)

        hotelService.getAllHotels().enqueue(object : Callback<List<HotelsResponse>> {
            override fun onResponse(call: Call<List<HotelsResponse>>, response: Response<List<HotelsResponse>>) {
                if (response.isSuccessful) {
                    val hotels = response.body()
                    Log.e("TAG", "onResponse: Best Hotels: $hotels")
                    mHotelsList.addAll(hotels!!)
                    hotelsResponseAdapter.notifyDataSetChanged()
                } else {
                    binding.tvChargerHotels.isVisible = true
                    binding.rvHotels.isVisible = false
                    Log.e("TAG", "onErrorHotels: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<HotelsResponse>>, t: Throwable) {
                binding.tvChargerHotels.isVisible = true
                binding.rvHotels.isVisible = false
                Log.e("TAG", "onFailureHotels: ${t.message}")
            }
        })
    }

    private fun getBestRestaurants() {
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
                    restaurantsResponseAdapter.notifyDataSetChanged()
                } else {
                    binding.tvChargerRestaurants.isVisible = true
                    binding.rvRestaurant.isVisible = false
                    Log.e("TAG", "onErrorRestaurants: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RestaurantsResponse>>, t: Throwable) {
                binding.tvChargerRestaurants.isVisible = true
                binding.rvRestaurant.isVisible = false
                Log.e("TAG", "onFailureRestaurants: ${t.message}")
            }
        })
    }
    private fun getBestBeaches() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-best-travel.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val beachService = retrofit.create(ApiService::class.java)

        beachService.getAllBeaches().enqueue(object : Callback<List<BeachesResponse>> {
            override fun onResponse(call: Call<List<BeachesResponse>>, response: Response<List<BeachesResponse>>) {
                if (response.isSuccessful) {
                    val beaches = response.body()
                    Log.e("TAG", "onResponse: Best Beaches: $beaches")
                    mBeachesList.addAll(beaches!!)
                    beachesResponseAdapter.notifyDataSetChanged()
                } else {
                    binding.rvBeaches.isVisible = false
                    binding.tvChargerBeaches.isVisible = true
                    Log.e("TAG", "onErrorBeaches: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<BeachesResponse>>, t: Throwable) {
                binding.rvBeaches.isVisible = false
                binding.tvChargerBeaches.isVisible = true
                Log.e("TAG", "onFailureBeaches: ${t.message}")
            }
        })
    }

    private fun initHotelAdapter() {
        hotelsResponseAdapter =
            DisplayHotelsResponseAdapter(requireContext(), mHotelsList, object : ItemClickListener {
                override fun onClick(position: Int) {
                    startActivity(
                        Intent(requireContext(), PlaceDetailsActivity::class.java)
                            .putExtra("isHotel", true)
                            .putExtra("HotelItem", mHotelsList[position])
                    )

                }

                override fun onFavClick(position: Int) {

                    val address = mHotelsList[position].address!!
                    if (!Constants.mStringList.contains(address)) {
                        val model = FavoriteModel(
                            mHotelsList[position].name!!, mHotelsList[position].description!!,
                            mHotelsList[position].images, mHotelsList[position].phone!!,
                            address
                        )
                        Constants.mFavoriteList.add(model)
                        Constants.mStringList.add(address)
                    } else {
                        val index = Constants.mStringList.indexOf(address)
                        Constants.mFavoriteList.removeAt(index)
                    }
                    AppPrefs.saveList(requireContext(),Constants.mFavoriteList)

                }

            })
        binding.rvHotels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHotels.adapter = hotelsResponseAdapter
    }

    private fun initBeachesAdapter() {
        beachesResponseAdapter = DisplayBeachesResponseAdapter(
            requireContext(),
            mBeachesList,
            object : ItemClickListener {
                override fun onClick(position: Int) {
                    startActivity(
                        Intent(requireContext(), PlaceDetailsActivity::class.java)
                            .putExtra("isBeach", true)
                            .putExtra("BeachItem", mBeachesList[position])
                    )
                }

                override fun onFavClick(position: Int) {
                    val address = mBeachesList[position].address!!
                    if (!Constants.mStringList.contains(address)) {
                        val model = FavoriteModel(
                            mBeachesList[position].name!!, mBeachesList[position].description!!,
                            mBeachesList[position].images, "",
                            address
                        )
                        Constants.mFavoriteList.add(model)
                        Constants.mStringList.add(address)
                    } else {
                        val index = Constants.mStringList.indexOf(address)
                        Constants.mFavoriteList.removeAt(index)
                    }
                    AppPrefs.saveList(requireContext(),Constants.mFavoriteList)

                }

            })
        binding.rvBeaches.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBeaches.adapter = beachesResponseAdapter
    }

    private fun initRestaurantsAdapter() {
        restaurantsResponseAdapter = DisplayRestaurantsResponseAdapter(
            requireContext(),
            mRestaurantsList,
            object : ItemClickListener {
                override fun onClick(position: Int) {
                    startActivity(
                        Intent(requireContext(), PlaceDetailsActivity::class.java)
                            .putExtra("isRestaurant", true)
                            .putExtra("RestaurantItem", mRestaurantsList[position])
                    )

                }

                override fun onFavClick(position: Int) {
                    val address = mRestaurantsList[position].address!!
                    if (!Constants.mStringList.contains(address)) {
                        val model = FavoriteModel(
                            mRestaurantsList[position].name!!, mRestaurantsList[position].description!!,
                            mRestaurantsList[position].images, mRestaurantsList[position].phone!!,
                            address
                        )
                        Constants.mFavoriteList.add(model)
                        Constants.mStringList.add(address)
                    } else {
                        val index = Constants.mStringList.indexOf(address)
                        Constants.mFavoriteList.removeAt(index)
                    }
                    AppPrefs.saveList(requireContext(),Constants.mFavoriteList)

                }

            })
        binding.rvRestaurant.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRestaurant.adapter = restaurantsResponseAdapter
    }
    private fun showDataReciclerViewBeaches() {
        binding.viewLoadingBeaches.isVisible = false
        binding.rvBeaches.isVisible = true
    }
    private fun showDataReciclerViewHotels() {
        binding.viewLoadingHotels.isVisible = false
        binding.rvHotels.isVisible = true
    }

    private fun showDataReciclerViewRestaurants() {
        binding.viewLoadingRest.isVisible = false
        binding.rvRestaurant.isVisible = true
    }
}