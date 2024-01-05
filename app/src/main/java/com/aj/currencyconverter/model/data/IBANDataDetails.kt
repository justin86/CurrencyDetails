package com.aj.currencyconverter.model.data

import com.google.gson.annotations.SerializedName


data class IBANDataDetails (

  @SerializedName("valid"                ) var valid              : Boolean?  = null,
  @SerializedName("message"              ) var message            : String?   = null,
  @SerializedName("iban"                 ) var iban               : String?   = null,
  @SerializedName("iban_data"            ) var ibanData           : IbanData? = IbanData(),
  @SerializedName("bank_data"            ) var bankData           : BankData? = BankData(),
  @SerializedName("country_iban_example" ) var countryIbanExample : String?   = null

)

