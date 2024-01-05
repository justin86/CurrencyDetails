package com.aj.currencyconverter.model.data

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class CountryCodeData (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("symbols" ) var countryListData : JsonObject?

)