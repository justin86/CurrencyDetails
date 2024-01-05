package com.aj.currencyconverter.model.data

import com.google.gson.annotations.SerializedName


data class BankData (
  var bankCode : String? = null,
  var name     : String? = null,
  var zip      : String? = null,
  var city     : String? = null,
  var bic      : String? = null
)