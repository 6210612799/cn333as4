package com.example.unitconverter.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.unitconverter.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    companion object {
        val screens = listOf(Temperature, Distances , Amount)
    }

    private object Temperature: Screen(
        "temperature",
        R.string.temperature,
        R.drawable.outline_thermostat_24
    )

    private object Distances: Screen(
        "distances",
        R.string.distances,
        R.drawable.outline_square_foot_24
    )

    private object Amount: Screen(
        "amount",
        R.string.amount,
        R.drawable.outline_water_drop_24
    )
}
