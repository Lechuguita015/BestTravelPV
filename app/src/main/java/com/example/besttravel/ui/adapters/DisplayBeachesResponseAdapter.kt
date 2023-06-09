package com.example.besttravel.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.besttravel.R
import com.example.besttravel.databinding.ItemViewBinding
import com.example.besttravel.models.beaches.BeachesResponse

import com.example.besttravel.ui.interfaces.ItemClickListener
@GlideModule
class DisplayBeachesResponseAdapter(val context: Context,
                                    private val mArrayList: ArrayList<BeachesResponse>,
                                    private val mItemClickInterface: ItemClickListener
): RecyclerView.Adapter<DisplayBeachesResponseAdapter.ViewHolder>() {




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
        fun bindItem(context: Context, model: BeachesResponse, mItemClickInterface: ItemClickListener)
        {
            binding.tvName.text =  model.name
            //binding.tvDesc.text = model.description
            if (model.images.isNullOrEmpty()) {
                Glide.with(context)
                    .load(R.drawable.imagerror) // Imagen por defecto
                    .placeholder(R.drawable.imagerror)
                    .into(binding.iv)
            } else {
                Glide.with(context)
                    .load(model.images!![0].urlImage)
                    .into(binding.iv)
            }
            binding.ivFav.setOnClickListener {
                mItemClickInterface.onFavClick(adapterPosition)
            }
            binding.root.setOnClickListener {
                mItemClickInterface.onClick(adapterPosition)
            }

        }
    }

}