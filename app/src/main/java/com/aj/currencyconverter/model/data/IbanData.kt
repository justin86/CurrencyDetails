package com.aj.currencyconverter.model.data

import com.google.gson.annotations.SerializedName


data class IbanData (


  @SerializedName("country"                      ) var country                  : String?  = null,
  @SerializedName("country_code"                 ) var countryCode              : String?  = null,
  @SerializedName("sepa_country"                 ) var sepaCountry              : Boolean? = null,
  @SerializedName("checksum"                     ) var checksum                 : String?  = null,
  @SerializedName("BBAN"                         ) var BBAN                     : String?  = null,
  @SerializedName("bank_code"                    ) var bankCode                 : String?  = null,
  @SerializedName("account_number"               ) var accountNumber            : String?  = null,
  @SerializedName("branch"                       ) var branch                   : String?  = null,
  @SerializedName("national_checksum"            ) var nationalChecksum         : String?  = null,
  @SerializedName("country_iban_format_as_swift" ) var countryIbanFormatAsSwift : String?  = null,
  @SerializedName("country_iban_format_as_regex" ) var countryIbanFormatAsRegex : String?  = null
)