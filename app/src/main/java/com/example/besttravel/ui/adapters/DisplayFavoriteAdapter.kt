package com.example.besttravel.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.besttravel.databinding.ItemViewBinding
import com.example.besttravel.models.beaches.BeachesResponse
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.databinding.ItemviewResponseBinding
import com.example.besttravel.models.FavoriteModel
import com.example.besttravel.ui.interfaces.ItemClickListener

class DisplayFavoriteAdapter(val context: Context,
                             private val mArrayList: ArrayList<FavoriteModel>,
                             private val mItemClickInterface: ItemClickListener
): RecyclerView.Adapter<DisplayFavoriteAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemViewBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(context,mArrayList[position],mItemClickInterface)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }


    class ViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bindItem(context: Context, model: FavoriteModel, mItemClickInterface: ItemClickListener)
        {
            binding.tvName.text =  model.itemName
            //binding.tvDesc.text = model.itemDesc
            Glide.with(context)
                .load(model.images[0].urlImage)
                .into(binding.iv)
            binding.ivFav.setOnClickListener {
                mItemClickInterface.onFavClick(adapterPosition)
            }
            binding.ivFav.isChecked = model.isFavorite
            binding.root.setOnClickListener {
                mItemClickInterface.onClick(adapterPosition)
            }

        }
    }

}