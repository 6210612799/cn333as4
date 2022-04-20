package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unitconverter.screens.AmountConverter
import com.example.unitconverter.screens.DistancesConverter
import com.example.unitconverter.screens.Screen
import com.example.unitconverter.screens.TemperatureConverter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverter()
        }
    }
}

@Composable
fun UnitConverter() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        bottomBar = {
            UnitConverterBottomBar(navController)
        }
    ) {
        UnitConveterNavHost(navController)
    }
}

@Composable
fun UnitConverterBottomBar(navController: NavHostController) {
    val selectedRoute = rememberSaveable { mutableStateOf(Screen.screens.first().route) }
    BottomNavigation {
        Screen.screens.forEach { screen ->
            BottomNavigationItem(
                selected = screen.route == selectedRoute.value,
                onClick = {
                    selectedRoute.value = screen.route
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.screens.first().route) {
                            inclusive = true
                        }
                    }
                },
                label = {
                    Text(text = stringResource(id = screen.label))
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = stringResource(id = screen.label)
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun UnitConveterNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.screens.first().route
    ) {
        composable(Screen.screens[0].route) {
            TemperatureConverter()
        }
        composable(Screen.screens[1].route) {
            DistancesConverter()
        }
        composable(Screen.screens[2].route) {
            AmountConverter()
        }
    }
}