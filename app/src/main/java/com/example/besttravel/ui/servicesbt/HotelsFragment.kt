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
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.databinding.FragmentHotelsBinding
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayHotelsResponseAdapter
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

class HotelsFragment : Fragment() {
    private lateinit var binding: FragmentHotelsBinding

    private var mHotelsList: ArrayList<HotelsResponse> = ArrayList()
    private var mHotelsComments: ArrayList<HotelsResponse> = ArrayList()
    //lateinit var hotelsResponseAdapter: DisplayHotelsResponseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(inflater,container,false)
        initHotelAdapter()
        getBestHotels()
        Handler(Looper.getMainLooper()).postDelayed({
            showDataRecyclerView()
        }, 3500)
        setHasOptionsMenu(true)
        // Cambiar el título del Action Bar
        (activity as AppCompatActivity).supportActionBar?.title = "Hotels"
        // Habilitar el botón de retroceso en el Action Bar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Establecer el icono del botón de retroceso
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
        return binding.root
    }

    //Consumo de API para obtener los hoteles
    private fun getBestHotels() {
        CoroutineScope(Dispatchers.IO).launch {
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
                        //hotelsResponseAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("TAG", "onError: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<HotelsResponse>>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }
            })
        }
    }
    private fun initHotelAdapter()
    {
        val adapter = DisplayHotelsResponseAdapter(requireContext(),mHotelsList,object : ItemClickListener
        {
            override fun onClick(position: Int) {
                startActivity(
                    Intent(requireContext(), PlaceDetailsActivity::class.java)
                    .putExtra("isHotel",true)
                    .putExtra("HotelItem",mHotelsList[position]))

            }
            override fun onFavClick(position: Int) {

            }

        })
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
    }
    private fun showDataRecyclerView() {
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