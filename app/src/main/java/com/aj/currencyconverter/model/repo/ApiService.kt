package com.aj.currencyconverter.model.repo


import com.aj.currencyconverter.model.data.CountryCodeData
import com.aj.currencyconverter.model.data.CurrencyConvertData
import com.aj.currencyconverter.model.data.IBANDataDetails
import com.aj.currencyconverter.model.data.LatestRateData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("bank_data/iban_validate")
    suspend fun getValidationIBAN(@Query("iban_number") iban_number: String): IBANDataDetails

    @GET("fixer/latest")
    suspend fun getLatesRates(@Query("base") base:String): LatestRateData

    @GET("fixer/symbols")
    suspend fun getCountryCodeList():CountryCodeData

    @GET("fixer/convert")
    suspend fun getCurrencyConvert(
        @Query("from") from : String,
        @Query("to") to : String,
        @Query("amount") amount : String) : CurrencyConvertData
}