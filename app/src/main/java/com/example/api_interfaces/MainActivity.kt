package com.example.api_interfaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppNavigation
import com.example.api_interfaces.ui.theme.API_InterfacesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = false

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Black,
                    darkIcons = useDarkIcons
                )
            }

            API_InterfacesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = MyViewModel()
                    AppNavigation(Modifier.padding(innerPadding),viewModel)
                }
            }
        }
    }
}