package com.aj.currencyconverter.model.data

import com.google.gson.annotations.SerializedName


data class CurrencyConvertData (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("query"   ) var query   : Query?   = Query(),
  @SerializedName("info"    ) var info    : Info?    = Info(),
  @SerializedName("date"    ) var date    : String?  = null,
  @SerializedName("result"  ) var result  : Double?  = null

)