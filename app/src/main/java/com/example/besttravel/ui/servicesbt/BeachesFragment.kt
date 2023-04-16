package com.example.besttravel.ui.servicesbt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.besttravel.ui.interfaces.ItemClickListener

class BeachesFragment : Fragment() {
    private lateinit var binding: FragmentBeachesBinding
    private var mBeachesList: ArrayList<BeachesResponse> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBeachesBinding.inflate(inflater, container, false)

        getBestBeaches()
        return binding.root
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
                        initBeachesAdapter()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: ${anError!!.message}")

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

}