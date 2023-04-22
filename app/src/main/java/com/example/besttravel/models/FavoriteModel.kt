package com.example.besttravel.models

import com.example.besttravel.models.hotels.Images
import java.util.*


data class FavoriteModel(
    val itemName: String,
    val itemDesc: String,
    val images: ArrayList<Images>,
    val phone: String,
    val address: String,
    var isFavorite: Boolean = false
) : java.io.Serializable