package com.example.besttravel.ui.interfaces

import com.example.besttravel.models.beaches.BeachesResponse
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.models.restaurants.RestaurantsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("service/hotel/all")
    fun getAllHotels(): Call<List<HotelsResponse>>

    @GET("service/restaurant/all")
    fun getAllRestaurants(): Call<List<RestaurantsResponse>>

    @GET("service/beach/all")
    fun getAllBeaches(): Call<List<BeachesResponse>>
}