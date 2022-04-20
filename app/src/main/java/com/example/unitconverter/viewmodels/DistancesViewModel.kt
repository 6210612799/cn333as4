package com.example.unitconverter.viewmodels

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverter.R

class DistancesViewModel : ViewModel() {
    private val _unit: MutableLiveData<Int> = MutableLiveData(R.string.meter)

    val unit: LiveData<Int>
        get() = _unit

    fun setUnit(value: Int) {
        _unit.value = value
    }

    private val _distance: MutableLiveData<String> = MutableLiveData("")

    val distance: LiveData<String>
        get() = _distance

    fun getDistanceAsFloat(): Float = (_distance.value ?: "").let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setDistance(value: String) {
        _distance.value = value
    }

    fun convert() = getDistanceAsFloat().let {
        if (!it.isNaN())
            if (_unit.value == R.string.meter)
                it * 0.00062137F
            else
                it / 0.00062137F
        else
            Float.NaN
    }
}