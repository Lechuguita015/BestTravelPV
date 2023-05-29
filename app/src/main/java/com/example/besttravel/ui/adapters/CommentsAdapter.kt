package com.example.besttravel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.example.besttravel.databinding.ItemviewCommentsBinding
import com.example.besttravel.models.beaches.Comments

@GlideModule
class CommentsAdapter(
    val context: Context,
    private val mCommentsList: ArrayList<Comments>)
    : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemviewCommentsBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mCommentsList[position])
    }

    override fun getItemCount(): Int = mCommentsList.size

    class ViewHolder(private val binding: ItemviewCommentsBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem(model: Comments){

            binding.tvEmailComments.text = model.email
            binding.tvComments.text = model.comment
            binding.rank.rating = model.qualification!!.toFloat()

        }
    }

}
