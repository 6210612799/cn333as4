package com.example.unitconverter.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.R
import com.example.unitconverter.viewmodels.AmountViewModel
import com.example.unitconverter.viewmodels.DistancesViewModel

@Composable
fun AmountConverter() {
    val viewModel: AmountViewModel = viewModel()
    val strOz = stringResource(id = R.string.oz)
    val strMl = stringResource(id = R.string.ml)
    val currentValue = viewModel.amount.observeAsState(viewModel.amount.value ?: "")
    val unit = viewModel.unit.observeAsState(viewModel.unit.value ?: R.string.oz)
    var result by rememberSaveable { mutableStateOf("") }
    val calc = {
        val temp = viewModel.convert()
        result = if (temp.isNaN())
            ""
        else
            "$temp${
                if (unit.value == R.string.oz)
                    strMl
                else strOz
            }"
    }
    val enabled by remember(currentValue.value) {
        mutableStateOf(!viewModel.getAmountAsFloat().isNaN())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AmountTextField(
            amount = currentValue,
            modifier = Modifier.padding(bottom = 16.dp),
            callback = calc,
            viewModel = viewModel
        )
        AmountScaleButtonGroup(
            selected = unit,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { resId: Int ->
            viewModel.setUnit(resId)
        }
        Button(
            onClick = calc,
            enabled = enabled
        ) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun AmountTextField(
    amount: State<String>,
    modifier: Modifier = Modifier,
    callback: () -> Unit,
    viewModel: AmountViewModel
) {
    TextField(
        value = amount.value,
        onValueChange = {
            viewModel.setAmount(it)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.placeholder_amount))
        },
        modifier = modifier,
        keyboardActions = KeyboardActions(onAny = {
            callback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun AmountScaleButtonGroup(
    selected: State<Int>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    val sel = selected.value
    Row(modifier = modifier) {
        AmountRadioButton(
            selected = sel == R.string.oz,
            resId = R.string.oz,
            onClick = onClick
        )
        AmountRadioButton(
            selected = sel == R.string.ml,
            resId = R.string.ml,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun AmountRadioButton(
    selected: Boolean,
    resId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick(resId)
            }
        )
        Text(
            text = stringResource(resId),
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

