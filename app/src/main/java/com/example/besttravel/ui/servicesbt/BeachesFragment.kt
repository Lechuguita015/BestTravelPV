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
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.besttravel.R
import com.example.besttravel.models.beaches.BeachesResponse
import com.example.besttravel.databinding.FragmentBeachesBinding
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayBeachesResponseAdapter
import com.example.besttravel.ui.interfaces.ApiService
import com.example.besttravel.ui.interfaces.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeachesFragment : Fragment() {
    private lateinit var binding: FragmentBeachesBinding
    private var mBeachesList: ArrayList<BeachesResponse> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBeachesBinding.inflate(inflater, container, false)
        initBeachesAdapter()
        getBestBeaches()
        Handler(Looper.getMainLooper()).postDelayed({
            showDataReciclerView()
        }, 3500)
        return binding.root
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
                    Log.e("TAG", "onResponse: Best Playas: $beaches")
                    mBeachesList.addAll(beaches!!)

                } else {
                    Log.e("TAG", "onError: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<BeachesResponse>>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }

    private fun initBeachesAdapter() {

        val adapter = DisplayBeachesResponseAdapter(
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

                }

            })
        binding.rvBeaches.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBeaches.adapter = adapter
    }

    private fun showDataReciclerView() {
        binding.viewLoading.isVisible = false
        binding.rvBeaches.isVisible = true
    }

}