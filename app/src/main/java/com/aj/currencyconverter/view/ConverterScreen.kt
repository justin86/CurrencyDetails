package com.aj.currencyconverter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aj.currencyconverter.R
import com.aj.currencyconverter.utils.GetSelectedValue
import com.aj.currencyconverter.viewmodel.BankViewModel

@Composable
fun Converter(viewModel: BankViewModel){
    val countryCodeList by viewModel._countryCode.observeAsState(emptyList())
    val convertedResult by viewModel._convertCurrency.observeAsState(initial = "")
    var isLoading by remember { mutableStateOf(false) }
    var fromPos by remember {
        mutableStateOf(
            ""
        )
    }
    var toPos by remember {
        mutableStateOf(
            ""
        )
    }


    var amoountTxt by remember {
        mutableStateOf(
            ""
        )
    }

    LaunchedEffect(Unit){
        viewModel.getCountryNames()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White).padding( 24f.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = stringResource(id = R.string.currency_converter),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )

            if (countryCodeList.isNotEmpty()){
                AppSpinner(
                    "From",
                    countryCodeList,
                    object : GetSelectedValue{
                        override fun setSelectedItemP(code: String) {
                            fromPos = code
                        }
                    }
                )
                AppSpinner(
                    "To", countryCodeList,
                    object : GetSelectedValue {
                        override fun setSelectedItemP(code: String) {
                            toPos = code
                        }
                    }
                )
                TextField(
                    value = amoountTxt,
                    onValueChange ={
                        amoountTxt = it },
                    label = { Text(text = stringResource(id = R.string.enter_amount)) },
                    placeholder = { Text(text = stringResource(id = R.string.amount)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)


                )
                Button(
                    onClick = {

                        if (fromPos.isNotEmpty() && toPos.isNotEmpty() && amoountTxt.isNotEmpty()){
                            viewModel.convertCurrency(fromPos, toPos, amoountTxt)
                            isLoading = isLoading.not()
                            viewModel._convertCurrency.value = null
                        }


                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                ){
                    Text(text = "Submit", color = Color.White )
                }

                if (!convertedResult.isNullOrEmpty()){
                    isLoading = false
                    Text(
                        text = " Result ${convertedResult}",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                }
            }



        }

        if (isLoading){
            Indicator()
        }
    }



}