package com.aj.currencyconverter.model.data

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class LatestRateData (

  @SerializedName("success"   ) var success   : Boolean? = null,
  @SerializedName("timestamp" ) var timestamp : Int?     = null,
  @SerializedName("base"      ) var base      : String?  = null,
  @SerializedName("date"      ) var date      : String?  = null,
  @SerializedName("rates"     ) var rates     : JsonObject?

)