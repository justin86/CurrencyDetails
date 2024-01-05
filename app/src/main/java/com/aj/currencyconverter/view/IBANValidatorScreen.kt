package com.aj.currencyconverter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.aj.currencyconverter.R
import com.aj.currencyconverter.model.data.IBANDataDetails
import com.aj.currencyconverter.viewmodel.BankViewModel

    lateinit var _viewModel : BankViewModel
    @Composable
    fun IBANValidator( viewModel : BankViewModel) {

    _viewModel = viewModel

    val ibanDetails by viewModel._ibanLiveData.observeAsState(null)
    var isLoading by remember { mutableStateOf(false) }

    var ibanTxt by remember {
        mutableStateOf(
            ""
        )
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (ibantxt, inputTxt, submitTxt, ibanColumn) = createRefs()

            Text(text = stringResource(id = R.string.validator_iban_number), modifier = Modifier.constrainAs(ibantxt) {
                top.linkTo(parent.top,  margin = Dp( 20f))
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)

            TextField(
                value = ibanTxt,
                onValueChange = {
                    ibanTxt = it
                },
                label = { Text(text =stringResource(id = R.string.enter_iban_number)) },
                placeholder = { Text(text = stringResource(id = R.string.iban)) },
                modifier = Modifier.constrainAs(inputTxt) {
                    top.linkTo(ibantxt.bottom, margin = Dp( 20f))
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Button(
                onClick = {

                    if (ibanTxt.isNotEmpty()){
                        viewModel.isValidIBAN(ibanTxt)
                        isLoading = isLoading.not()
                        viewModel._ibanLiveData.value = null
                    }


                },
                modifier = Modifier.constrainAs(submitTxt) {
                    top.linkTo(inputTxt.bottom, margin = Dp( 20f))
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){
                Text(text = "Submit", color = Color.White )


            }


            Card(
                modifier = Modifier.constrainAs(ibanColumn) {
                    top.linkTo(submitTxt.bottom, margin = Dp( 20f))
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),

            ){
                if (ibanDetails != null){
                    isLoading = false
                    ibanDetailsView(ibanDetails!!)
                }


            }

        }

        if (isLoading){
            Indicator()
        }

    }

}

    @Composable
    fun ibanDetailsView(ibanDetails: IBANDataDetails) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            bankDetailsItem("${ibanDetails.message}")
            bankDetailsItem("IBAN Detaial", fs = 24.sp )
            bankDetailsItem(" Country : ${ibanDetails.ibanData?.country}")
            bankDetailsItem("Country code : ${ibanDetails.ibanData?.countryCode}")
            bankDetailsItem("BBAN : ${ibanDetails.ibanData?.BBAN}")
            bankDetailsItem("Bank code : ${ibanDetails.ibanData?.bankCode}")
            bankDetailsItem("Account Number : ${ibanDetails?.ibanData?.accountNumber}")
            bankDetailsItem("Bank Details", fs = 24.sp)
            bankDetailsItem("Bank Code : ${ibanDetails.bankData?.bankCode}")
            bankDetailsItem("Bank Name : ${ibanDetails.bankData?.name}")
            bankDetailsItem("Zip : ${ibanDetails.bankData?.zip}")
            bankDetailsItem("City : ${ibanDetails.bankData?.city}")


        }
    }

    @Composable
    fun bankDetailsItem(str : String, fs : TextUnit =  16.sp){

Column {
Text(
    text = "$str",
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = fs
)
Spacer(modifier = Modifier.padding(start = 15.dp))
}

}




    @Preview(showSystemUi = true)
    @Composable
    fun IBANValidatorPreview() {
    //IBANValidator()
    }