package com.example.besttravel.models.restaurants

import com.example.besttravel.models.beaches.Comments
import com.example.besttravel.models.hotels.Images
import com.google.gson.annotations.SerializedName


data class RestaurantsResponse (

    @SerializedName("id"          ) var id          : Int?                  = null,
    @SerializedName("name"        ) var name        : String?               = null,
    @SerializedName("description" ) var description : String?               = null,
    @SerializedName("ranking"     ) var ranking     : Double?               = null,
    @SerializedName("address"     ) var address     : String?               = null,
    @SerializedName("phone"       ) var phone       : String?               = null,
    @SerializedName("images"      ) var images      : ArrayList<Images>?    = arrayListOf(),
    @SerializedName("comments"    ) var comments    : ArrayList<Comments>?  = arrayListOf(),
    var isFavorite: Boolean = false

):java.io.Serializable