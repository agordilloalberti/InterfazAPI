package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppScreen

@Composable
fun APITareas(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel) {

    val token by viewModel.token.observeAsState("")
    val rol by viewModel.rol.observeAsState("")
    val dismissed by viewModel.dismissed.observeAsState(false)
    val tareas by viewModel.tareas.observeAsState()

    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (tareas.isNullOrEmpty()) {
            if (!dismissed) {
                AddAlertDialog(
                    "Advertencia",
                    "No existen tareas para este usuario"
                ) { viewModel.dismiss() }
            }
            Box(Modifier.fillMaxSize()) {
                if (rol == "ROLE_ADMIN") {
                    AddButton(
                        "Añadir tarea",
                        Modifier.align(Alignment.Center)
                    ) {
                        navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                        "insertA"
                    )
                    }
                } else {
                    AddButton(
                        "Añadir tarea",
                        Modifier.align(Alignment.Center)
                    ) {
                        navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                        "insertN"
                    )
                    }
                }
            }
        }

//    LazyColumn(modifier.fillMaxSize().background(Color.Black)){
//        item{Column {
//                for (tarea in tareas!!){
//
//                }
//            }
//        }}
    }
}