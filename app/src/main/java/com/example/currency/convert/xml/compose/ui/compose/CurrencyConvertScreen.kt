package com.example.currency.convert.xml.compose.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.currency.convert.xml.compose.R
import com.example.currency.convert.xml.compose.ui.CurrencyViewModel


@Composable
fun ConvertScreen(
    viewModel: CurrencyViewModel,
) {
    ConstraintLayout {

        val list = stringArrayResource(id = R.array.currency_codes)
        val expandedFrom = remember { mutableStateOf(false) }
        val expandedTo = remember { mutableStateOf(false) }
        val currentValueFrom = remember { mutableStateOf(list[0]) }
        val currentValueTo = remember { mutableStateOf(list[0]) }
        var amount  = remember { mutableStateOf("") }

        //  Create guideline from 16 dp from the top of the parent
        val topGuideline = createGuidelineFromTop(0.5f)

        // Create references for the composables to constrain
        val (titleApp, textFrom, textTo, amountTextField, convertButton, resultText, progressBar, spinnerCurrencyFrom, spinnerCurrencyTo) = createRefs()


        TitleApp(Modifier.constrainAs(titleApp){
            top.linkTo(parent.top,16.dp)
            start.linkTo(parent.start,16.dp)
            bottom.linkTo(topGuideline,10.dp)
        })

        Text(
            text = stringResource(id = R.string.from),
            Modifier
                .padding(15.dp)
                .constrainAs(textFrom) {
                    top.linkTo(topGuideline, 5.dp)
                    start.linkTo(spinnerCurrencyFrom.start)
                }
        )
        Text(
            text = stringResource(id = R.string.to),
            Modifier
                .padding(15.dp)
                .constrainAs(textTo) {
                    top.linkTo(topGuideline, 5.dp)
                    start.linkTo(spinnerCurrencyTo.start)
                }
        )


        Box(modifier = Modifier
            .padding(15.dp)
            .constrainAs(spinnerCurrencyFrom) {
                top.linkTo(textFrom.bottom)
                end.linkTo(spinnerCurrencyTo.start, 16.dp)
            },
        ) {

            Row(modifier = Modifier
                .clickable {
                    expandedFrom.value = !expandedFrom.value
                }
                .align(Alignment.Center)) {
                Text(text = currentValueFrom.value)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)


                DropdownMenu(expanded = expandedFrom.value, onDismissRequest = {
                    expandedFrom.value = false
                }) {

                    list.forEach {

                        DropdownMenuItem(onClick = {
                            currentValueFrom.value = it
                            expandedFrom.value = false
                        }) {

                            Text(text = it)

                        }

                    }

                }


            }

        }

        Box(modifier = Modifier
            .padding(15.dp)
            .constrainAs(spinnerCurrencyTo) {
                top.linkTo(textTo.bottom)
                end.linkTo(parent.end)
            },
        ) {

            Row(modifier = Modifier
                .clickable {
                    expandedTo.value = !expandedTo.value
                }
                .align(Alignment.Center)) {
                Text(text = currentValueTo.value)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)


                DropdownMenu(expanded = expandedTo.value, onDismissRequest = {
                    expandedTo.value = false
                }) {

                    list.forEach {

                        DropdownMenuItem(onClick = {
                            currentValueTo.value = it
                            expandedTo.value = false
                        }) {

                            Text(text = it)

                        }

                    }

                }


            }

        }


        OutlinedTextField(
            value = amount.value,
            onValueChange = {
                amount.value= it
            },
            label = {
                Text(stringResource(id = R.string.amount))
            },
            placeholder = {
                Text(stringResource(id = R.string.amount))
            },
            modifier = Modifier
                .constrainAs(amountTextField) {
                    start.linkTo(parent.start)
                    top.linkTo(spinnerCurrencyFrom.top)
                    bottom.linkTo(spinnerCurrencyFrom.bottom)
                    end.linkTo(spinnerCurrencyFrom.start, 16.dp)
                }
                .width(100.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )

        )

        ConvertButton(
            {

                viewModel.convert(
                    amount.value,
                    currentValueFrom.value,
                    currentValueTo.value
                )

            },
            modifier = Modifier.constrainAs(convertButton){

                top.linkTo(spinnerCurrencyTo.bottom,16.dp)
                end.linkTo(parent.end,16.dp)
            }
        )

        if (viewModel.isVisible){
            ProgressBar(
                modifier = Modifier.constrainAs(progressBar){
                    top.linkTo(convertButton.top)
                    bottom.linkTo(convertButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(convertButton.start)
                }
            )
        }else{
            ResultText(
                result = viewModel.txtResult,
                modifier = Modifier.constrainAs(resultText){
                    top.linkTo(convertButton.top)
                    bottom.linkTo(convertButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(convertButton.start)

                }
            )
        }


    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
private fun TitleApp(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.app_name),
        fontWeight = FontWeight.Bold,
        fontSize = dpToSp(dp = 32.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
    )
}




@Composable
fun SpinnerCurrency(
    modifier: Modifier,
    viewModel: CurrencyViewModel,

) {

    val list = stringArrayResource(id = R.array.currency_codes)
    val expanded = remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(list[0]) }



    Box(modifier = modifier
        .padding(15.dp)
    ) {

        Row(modifier = modifier
            .clickable {
                expanded.value = !expanded.value
            }
            .align(Alignment.Center)) {
            Text(text = currentValue.value)
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)


            DropdownMenu(expanded = expanded.value, onDismissRequest = {
                expanded.value = false
            }) {

                list.forEach {

                    DropdownMenuItem(onClick = {
                        currentValue.value = it
                        expanded.value = false
                    }) {

                        Text(text = it)

                    }

                }

            }


        }

    }



}

@Composable
private fun ConvertButton(
   onConvertClick : ()-> Unit,
   modifier: Modifier
){

    Button(
        onClick = onConvertClick,
        modifier = modifier

    ) {
        Text(stringResource(id = R.string.btnConvert))
    }

}

@Composable
private fun ProgressBar(
    modifier: Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.size(size = 30.dp),
        strokeWidth = 3.dp
    )
}


@Composable
private fun ResultText(
    result: String,
    modifier: Modifier
){

    Text(
        text = result,
        modifier = modifier
    )
}



@Composable
@Preview
fun ConvertScreenPreview(){
    //ConvertScreen()
}






