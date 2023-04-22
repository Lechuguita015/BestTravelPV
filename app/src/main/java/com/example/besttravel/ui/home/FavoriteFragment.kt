package com.example.besttravel.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besttravel.databinding.FragmentFavoriteBinding
import com.example.besttravel.models.FavoriteModel
import com.example.besttravel.ui.PlaceDetailsActivity
import com.example.besttravel.ui.adapters.DisplayBeachesResponseAdapter
import com.example.besttravel.ui.adapters.DisplayFavoriteAdapter
import com.example.besttravel.ui.adapters.DisplayHotelsResponseAdapter
import com.example.besttravel.ui.adapters.DisplayRestaurantsResponseAdapter
import com.example.besttravel.ui.interfaces.ItemClickListener
import com.example.besttravel.utils.AppPrefs
import com.example.besttravel.utils.Constants


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: DisplayFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        initBeachesAdapter()
        return binding.root
    }
    private fun initBeachesAdapter() {
        adapter = DisplayFavoriteAdapter(
            requireContext(),
            Constants.mFavoriteList,
            object : ItemClickListener {
                override fun onClick(position: Int) {

                }

                override fun onFavClick(position: Int) {
                    val address = Constants.mFavoriteList[position].address
                    val isChecked = !Constants.mFavoriteList[position].isFavorite

                    if (!Constants.mStringList.contains(address)) {
                        val model = FavoriteModel(
                            Constants.mFavoriteList[position].itemName,
                            Constants.mFavoriteList[position].itemDesc,
                            Constants.mFavoriteList[position].images,
                            Constants.mFavoriteList[position].phone,
                            address
                        )
                        model.isFavorite = isChecked
                        Constants.mFavoriteList.add(model)
                        Constants.mStringList.add(address)
                    } else {
                        val index = Constants.mStringList.indexOf(address)
                        Constants.mFavoriteList[index].isFavorite = isChecked
                        if (!isChecked) {
                            Constants.mFavoriteList.removeAt(index)
                            Constants.mStringList.removeAt(index)
                        }
                    }

                    AppPrefs.saveList(requireContext(), Constants.mFavoriteList)
                    adapter.notifyItemChanged(position)
                }

            })
        for (favorite in Constants.mFavoriteList) {
            if (Constants.mStringList.contains(favorite.address)) {
                favorite.isFavorite = true
                binding.rv.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.rv.adapter = adapter
            }

        }
    }

}