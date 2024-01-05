package com.aj.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.currencyconverter.model.data.IBANDataDetails
import com.aj.currencyconverter.model.data.LatestRateData
import com.aj.currencyconverter.model.data.ListRates
import com.aj.currencyconverter.model.repo.BankRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject
constructor( private val bankRepository :  BankRepository ):  ViewModel() {

    var _ibanLiveData  = MutableLiveData<IBANDataDetails>()
    var _ratesLiveData = MutableLiveData<List<ListRates>>()
    var _countryCode = MutableLiveData<List<String>>()
    lateinit var currentRate : JsonObject
    var _convertCurrency = MutableLiveData<String>()

    fun isValidIBAN(iban_number : String ){
        viewModelScope.launch {
            try {
                _ibanLiveData.value = bankRepository.checkIBAN(iban_number)

            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun getRateBaseKWD(){
        viewModelScope.launch {
            try {
                val data = bankRepository.getLatestRates()
                currentRate = data.rates!!
                val ratesKeys = currentRate.keySet()
                val ratesData = ArrayList<ListRates>()
                ratesKeys?.forEach {
                    ratesData.add(ListRates(countryCode = it, rates = data.rates!!.get(it).asDouble))
                }
                _ratesLiveData.value = ratesData

            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun getCountryNames(){
        viewModelScope.launch {
            try {
                val data = bankRepository.getCountryCodeList()
                _countryCode.value = data.countryListData?.keySet()?.toList()

            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }
    fun convertCurrency(from : String, to : String, amount : String){
        viewModelScope.launch {
            try {

                val data = bankRepository.getCurrencyConvertApi(from, to, amount)
                _convertCurrency.value = data.result.toString()
            }catch (e : Exception){
                e.printStackTrace()
            }
        }

    }

}