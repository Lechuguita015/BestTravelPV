package com.example.besttravel.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.besttravel.R
import com.example.besttravel.models.FavoriteModel
import com.example.besttravel.models.beaches.BeachesResponse
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.models.restaurants.RestaurantsResponse
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayBeachesResponseAdapter
import com.example.besttravel.ui.adapters.DisplayHotelsResponseAdapter
import com.example.besttravel.ui.adapters.DisplayRestaurantsResponseAdapter
import com.example.besttravel.ui.interfaces.ItemClickListener
import com.example.besttravel.ui.servicesbt.ServicesbtActivity
import com.example.besttravel.utils.AppPrefs
import com.example.besttravel.utils.Constants


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btnRestaurant = view.findViewById<ImageButton>(R.id.iv_restaurant)
        val btnBeaches = view.findViewById<ImageButton>(R.id.iv_beaches)
        val btnHotel = view.findViewById<ImageButton>(R.id.iv_hotel)
        val btnCars = view.findViewById<ImageButton>(R.id.iv_rents_cars)
        //initialize all RecyclerView
        rvHotels = view.findViewById<RecyclerView>(R.id.rv_hotels)
        rvRestaurants = view.findViewById<RecyclerView>(R.id.rv_restaurant)
        rvBeaches = view.findViewById<RecyclerView>(R.id.rv_beaches)

        btnRestaurant.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_restaurant)
            startActivity(intent)
        }
        btnBeaches.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_beaches)
            startActivity(intent)
        }
        btnHotel.setOnClickListener {
            val intent = Intent(activity, ServicesbtActivity::class.java)
            intent.putExtra("FRAGMENT_ID", R.id.fragment_hotel)
            startActivity(intent)
        }
        btnCars.setOnClickListener {
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

        return view
    }

    private fun getBestHotels() {
        AndroidNetworking.get("https://api-best-travel.azurewebsites.net/api/service/hotel/all")
            .setPriority(com.androidnetworking.common.Priority.HIGH)
            .build()
            .getAsObjectList(
                HotelsResponse::class.java,
                object : ParsedRequestListener<ArrayList<HotelsResponse>> {
                    override fun onResponse(response: ArrayList<HotelsResponse>) {
                        Log.e("TAG", "onResponse: Best Restaurants: $response")


                        mHotelsList.addAll(response)
                        this@HomeFragment.hotelsResponseAdapter.notifyDataSetChanged()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: ${anError!!.message}")

                    }
                })
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
                       restaurantsResponseAdapter.notifyDataSetChanged()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: ${anError!!.message}")

                    }
                })
    }

    private fun getBestBeaches() {
        AndroidNetworking.get("https://api-best-travel.azurewebsites.net/api/service/beach/all")
            .setPriority(com.androidnetworking.common.Priority.HIGH)
            .build()
            .getAsObjectList(
                BeachesResponse::class.java,
                object : ParsedRequestListener<ArrayList<BeachesResponse>> {
                    override fun onResponse(response: ArrayList<BeachesResponse>) {
                        Log.e("TAG", "onResponse: Best Restaurants: $response")


                        mBeachesList.addAll(response)
                       beachesResponseAdapter.notifyDataSetChanged()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: ${anError!!.message}")

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
        rvHotels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvHotels.adapter = hotelsResponseAdapter
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
        rvBeaches.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvBeaches.adapter = beachesResponseAdapter
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
        rvRestaurants.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvRestaurants.adapter = restaurantsResponseAdapter
    }
}