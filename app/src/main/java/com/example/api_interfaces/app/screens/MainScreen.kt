package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.Footer
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.Toolbar
import com.example.api_interfaces.app.navigation.AppScreen
import kotlin.system.exitProcess

/**
 * Pantala princiapl conteniendo el acceso a la api, las opciones (WIP) y un boton para cerrar la app
 */

@Composable
fun MainScreen(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel){
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            Toolbar(navControlador, Modifier, AppScreen.MainScreen.route)
            Body(navControlador, Modifier.align(Alignment.Start))
        }
        Footer(Modifier.align(Alignment.BottomStart))
    }
}

@Composable
private fun Body(navControlador: NavController, modifier: Modifier){
    ConstraintLayout(modifier = modifier.fillMaxSize().background(Color.Black)) {
        val (buttonAPI,buttonOptions,buttonExit) = createRefs()

        AddButton("Acceder a la API",
            Modifier
                .padding(5.dp)
                .constrainAs(buttonAPI) {
                    top.linkTo(parent.top)
                    bottom.linkTo(buttonOptions.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {navControlador.navigate(AppScreen.APIMenu.route)}

        AddButton("Options",Modifier
            .padding(5.dp)
            .constrainAs(buttonOptions)
            {
                top.linkTo(buttonAPI.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(buttonExit.start)
            })

        AddButton("Exit",Modifier
            .padding(5.dp)
            .constrainAs(buttonExit)
            {
                top.linkTo(buttonAPI.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(buttonOptions.end)
                end.linkTo(parent.end)
            }) { exitProcess(0) }
    }
}