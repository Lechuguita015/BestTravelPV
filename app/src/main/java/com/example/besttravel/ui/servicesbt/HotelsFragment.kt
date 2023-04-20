package com.example.besttravel.ui.servicesbt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.databinding.FragmentHotelsBinding
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayHotelsResponseAdapter
import com.example.besttravel.ui.interfaces.ItemClickListener

class HotelsFragment : Fragment() {
    private lateinit var binding: FragmentHotelsBinding

    private var mHotelsList: ArrayList<HotelsResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(inflater,container,false)
        getBestHotels()
        Handler(Looper.getMainLooper()).postDelayed({
            showDataReciclerView()
        }, 3000)
        return binding.root
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
                        initHotelAdapter()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: ${anError!!.message}")

                    }
                })
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
    private fun showDataReciclerView() {
        binding.viewLoading.isVisible = false
        binding.rv.isVisible = true
    }

}