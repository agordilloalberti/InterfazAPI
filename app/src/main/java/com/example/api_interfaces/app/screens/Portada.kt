package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.AddPlainText
import com.example.api_interfaces.app.Footer
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.Toolbar
import com.example.api_interfaces.app.navigation.AppScreen

/**
 * Portada simple para mostrar el objetivo de la app
 */
@Composable
fun Portada(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            Toolbar(navControlador, Modifier.wrapContentHeight().fillMaxWidth(), AppScreen.Portada.route)
            Body(navControlador, Modifier.align(Alignment.CenterHorizontally))
        }
        Footer(Modifier.align(Alignment.BottomStart))
    }

}

@Composable
private fun Body(navControlador: NavController, modifier: Modifier){
    Column(modifier = modifier.background(Color.Black).fillMaxSize())
    {
        AddPlainText("Bienvenido a la aplicación engargada de consumir la API creada con diego.\n\n" +
                "Acceda al menú principal mediante el botón situado aqui abajo o " +
                "mediante la barra de herramientas a la cabeza de la pantalla")

        AddButton("Ir al menú principal",
            Modifier.padding(50.dp).align(Alignment.CenterHorizontally)
        ){navControlador.navigate(AppScreen.MainScreen.route)}
    }
}