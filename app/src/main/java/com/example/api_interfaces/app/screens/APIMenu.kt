package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.Footer
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.Toolbar
import com.example.api_interfaces.app.navigation.AppScreen

/**
 * Menu que contiene las opciones princiaples, login, registrar y acceder a las operaciones de tareas.
 * Tambien cuenta con un boton para volver
 */
@Composable
fun APIMenu(navControlador: NavHostController, modifier: Modifier, viewModel: MyViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            Toolbar(navControlador, Modifier, AppScreen.APIMenu.route)
            Body(navControlador, Modifier,viewModel)
        }
        Footer(Modifier.align(Alignment.BottomStart))
    }
}

@Composable
private fun Body(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel) {
    Column(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AddButton("Login",Modifier.weight(1f)) { navControlador.navigate(AppScreen.APIUser.route); viewModel.changeScreen("login") }
            AddButton("Registrar usuario",Modifier.weight(1f)) { navControlador.navigate(AppScreen.APIUser.route); viewModel.changeScreen("register") }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AddButton("Volver",Modifier.weight(1f)) { navControlador.navigate(AppScreen.MainScreen.route) }
        }
    }
}
