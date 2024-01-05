package com.aj.currencyconverter.model.repo

import com.aj.currencyconverter.model.data.CountryCodeData
import com.aj.currencyconverter.model.data.CurrencyConvertData
import com.aj.currencyconverter.model.data.IBANDataDetails
import com.aj.currencyconverter.model.data.LatestRateData
import javax.inject.Inject

class BankRepository @Inject constructor(private val callApiService : ApiService ) {

    suspend fun checkIBAN(iban_number : String):IBANDataDetails{
        return callApiService.getValidationIBAN(iban_number)
    }

    suspend fun getLatestRates() : LatestRateData{
        return callApiService.getLatesRates("KWD")
    }

    suspend fun getCountryCodeList() : CountryCodeData{
        return callApiService.getCountryCodeList()
    }

    suspend fun getCurrencyConvertApi(from : String , to : String , amount : String) : CurrencyConvertData {
        return callApiService.getCurrencyConvert(from, to, amount)
    }

}