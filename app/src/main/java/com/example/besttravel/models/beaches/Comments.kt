package com.example.besttravel.models.beaches

import com.google.gson.annotations.SerializedName


data class Comments (

  @SerializedName("id"            ) var id            : Int?    = null,
  @SerializedName("email"         ) var email         : String? = null,
  @SerializedName("comment"       ) var comment       : String? = null,
  @SerializedName("qualification" ) var qualification : Double?    = null,
  @SerializedName("place"         ) var place         : Int?    = null,
  @SerializedName("idPlace"       ) var idPlace       : Int?    = null

):java.io.Serializable