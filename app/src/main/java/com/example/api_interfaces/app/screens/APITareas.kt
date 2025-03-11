package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.AddPlainText
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppScreen

@Composable
fun APITareas(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel) {

    val token by viewModel.token.observeAsState("")
    val rol by viewModel.rol.observeAsState("")
    val dismissed by viewModel.dismissed.observeAsState(false)

    if (token.isBlank() && !dismissed){
        AddAlertDialog("Error","Debes estar logeado para acceder a estas funciones") {viewModel.dismiss();navControlador.navigate(AppScreen.APIMenu.route)}
    }

    Column(modifier.fillMaxSize().background(Color.Black)){
        Row() {
            Column(Modifier.weight(1f)) {
                AddPlainText("NORMAL USER")
                AddButton("Insertar tarea") {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "insertN"
                )
                }
                AddButton("Obtener tarea") {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "getN"
                )
                }
                AddButton("Actualizar tarea") {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "updateN"
                )
                }
                AddButton("Completar tarea") {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "completeN"
                )
                }
                AddButton("Descompletar tarea") {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "uncompleteN"
                )
                }
                AddButton("Borra tarea") {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "deleteN"
                )
                }
            }

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = Color.White
            )

            Column(Modifier.weight(1f)) {
                AddPlainText("ADMIN USER")
                AddButton("Insertar tarea", enabled = rol == "ROLE_ADMIN") {
                    navControlador.navigate(
                        AppScreen.APITareasOperations.route
                    );viewModel.changeScreen("insertA")
                }
                AddButton(
                    "Obtener tarea de usuario",
                    enabled = rol == "ROLE_ADMIN"
                ) {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "getA"
                )
                }
                AddButton("Actualizar tarea", enabled = rol == "ROLE_ADMIN") {
                    navControlador.navigate(
                        AppScreen.APITareasOperations.route
                    );viewModel.changeScreen("updateA")
                }
                AddButton("Completar tarea", enabled = rol == "ROLE_ADMIN") {
                    navControlador.navigate(
                        AppScreen.APITareasOperations.route
                    );viewModel.changeScreen("completeA")
                }
                AddButton(
                    "Descompletar tarea",
                    enabled = rol == "ROLE_ADMIN"
                ) {
                    navControlador.navigate(AppScreen.APITareasOperations.route);viewModel.changeScreen(
                    "uncompleteA"
                )
                }
                AddButton("Borra tarea", enabled = rol == "ROLE_ADMIN") {
                    navControlador.navigate(
                        AppScreen.APITareasOperations.route
                    );viewModel.changeScreen("deleteA")
                }
            }
        }

        AddButton("Volver") {
            navControlador.navigate(AppScreen.APIMenu.route)
            viewModel.changeUser("")
            viewModel.reset()
        }

    }

}