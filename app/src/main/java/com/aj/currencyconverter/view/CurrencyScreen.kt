package com.aj.currencyconverter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aj.currencyconverter.R
import com.aj.currencyconverter.model.data.ListRates
import com.aj.currencyconverter.viewmodel.BankViewModel

@Composable
fun Currency(viewModel : BankViewModel) {
    val ratesList by viewModel._ratesLiveData.observeAsState(emptyList())
    LaunchedEffect(Unit){
        viewModel.getRateBaseKWD()
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.all_currency),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {

            items(ratesList!!) { rate ->
                cardItem(rate)
                /*Text(
                    "${rate.countryCode}    :     ${rate.rates}",
                    style = MaterialTheme.typography.bodyMedium
                )*/
               // PlantCard(plant.name, plant.description, plant.imageRes)
            }
        }


    }

}

@Composable
fun cardItem(rate : ListRates){
    Card(
        modifier = Modifier
            .padding(0.2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation =  CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors()
    ) {
        Row (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 8.dp)){
            Text(text = "${rate.countryCode}" )
            Spacer(modifier = Modifier.padding(start = 30.dp))
            Text(text = "-" )
            Spacer(modifier = Modifier.padding(start = 30.dp))
            Text(text = "${rate.rates}" )

        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CurrencyConverterPreview() {
    //CurrencyConverter()
}