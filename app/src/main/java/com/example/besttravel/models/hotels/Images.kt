package com.example.besttravel.models.hotels

import com.google.gson.annotations.SerializedName


data class Images (

  @SerializedName("id"       ) var id       : Int?    = null,
  @SerializedName("place"    ) var place    : Int?    = null,
  @SerializedName("idPlace"  ) var idPlace  : Int?    = null,
  @SerializedName("urlImage" ) var urlImage : String? = null

):java.io.Serializable