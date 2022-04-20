package com.example.unitconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverter.R

class AmountViewModel:  ViewModel() {
    private val _unit: MutableLiveData<Int> = MutableLiveData(R.string.oz)

    val unit: LiveData<Int>
        get() = _unit

    fun setUnit(value: Int) {
        _unit.value = value
    }

    private val _amount: MutableLiveData<String> = MutableLiveData("")

    val amount: LiveData<String>
        get() = _amount

    fun getAmountAsFloat(): Float = (_amount.value ?: "").let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setAmount(value: String) {
        _amount.value = value
    }

    fun convert() = getAmountAsFloat().let {
        if (!it.isNaN())
            if (_unit.value == R.string.oz)
                it * 28.41F
            else
                it / 28.41F
        else
            Float.NaN
    }
}