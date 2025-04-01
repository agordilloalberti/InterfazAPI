package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.AddPlainText
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppScreen

@Composable
fun APITareas(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel) {

    viewModel.update()

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
                    ) {navControlador.navigate(AppScreen.APITareasOperations.route);
                        viewModel.changeScreen("insertN") }
                }
            }
        } else {
            LazyColumn(modifier.fillMaxSize().background(Color.Black)){

                for (tarea in tareas!!){

                    item {
                        val estado:String
                        val op : Unit
                        if (tarea.completada){
                            estado="Descompletar"
                            op = if (rol=="ROLE_ADMIN"){
                                viewModel.uncompleteTareaA(token,tarea.name)
                            }else{
                                viewModel.uncompleteTareaN(token,tarea.name)
                            }
                        }else{
                            estado="Completar"
                            op = if (rol=="ROLE_ADMIN"){
                                viewModel.completeTareaA(token,tarea.name)
                            }else{
                                viewModel.completeTareaN(token,tarea.name)
                            }
                        }

                        Row {
                            AddPlainText(tarea.name)
                            Column {
                                AddButton("Editar")
                                {
                                    navControlador.navigate(AppScreen.APITareasOperations.route)
                                    if (rol=="ROLE_ADMIN") {
                                        viewModel.changeScreen("UpdateA")
                                    }else{
                                        viewModel.changeScreen("UpdateN")
                                    }
                                }
                                AddButton("Borrar")
                                {
                                    if (rol=="ROLE_ADMIN"){
                                        viewModel.deleteTareaA(token,tarea.name)
                                    }else{
                                        viewModel.deleteTareaN(token,tarea.name)
                                    }
                                }

                                AddButton(estado, onclick = {op})
                            }
                        }
                    }
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AddButton("Añadir tarea")
                            {navControlador.navigate(AppScreen.APITareasOperations.route);
                                viewModel.changeScreen("insertA")}
                        AddButton("Cerrar sesión")
                        {navControlador.navigate(AppScreen.APIMenu.route)}
                    }
                }
            }
        }
    }
}