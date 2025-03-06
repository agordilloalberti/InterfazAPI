package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.Footer
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.Toolbar
import com.example.api_interfaces.app.navigation.AppScreen

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
private fun Body(navControlador: NavController, modifier: Modifier,viewModel: MyViewModel){
    Column(modifier = modifier
        .background(Color.Black)
        .fillMaxSize())
    {
        AddButton("Login") {navControlador.navigate(AppScreen.APIUser.route);viewModel.changeScreen("login")}
        AddButton("Registrar usuario") {navControlador.navigate(AppScreen.APIUser.route);viewModel.changeScreen("register")}
        AddButton("Gestionar tareas") {navControlador.navigate(AppScreen.APITareas.route)}
        AddButton("Volver") {navControlador.navigate(AppScreen.MainScreen.route)}
    }
}