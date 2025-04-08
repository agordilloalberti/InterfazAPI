package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
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
            LazyColumn(modifier.fillMaxSize().background(Color.Black)) {

                items(tareas!!) { tarea ->
                    val estado = if (tarea.completada) "Descompletar" else "Completar"

                    Row(Modifier.fillMaxWidth().border(1.dp,Color.White).padding(1.dp)) {
                        Text(tarea.name,Modifier.wrapContentSize().weight(2f),Color.White)
                        Column(Modifier.wrapContentSize().weight(1f)) {

                            AddButton("Editar",Modifier.wrapContentSize().align(Alignment.End)) {
                                navControlador.navigate(AppScreen.APITareasOperations.route)
                                viewModel.changeScreen(if (rol == "ROLE_ADMIN") "UpdateA" else "UpdateN")
                            }

                            AddButton("Borrar",Modifier.wrapContentSize().align(Alignment.End)) {
                                if (rol == "ROLE_ADMIN") {
                                    run {
                                        viewModel.deleteTareaA(token, tarea.name)
                                        viewModel.update()
                                    }
                                } else {
                                    run {
                                        viewModel.deleteTareaN(token, tarea.name)
                                        viewModel.update()
                                    }
                                }
                            }

                            AddButton(estado,Modifier.wrapContentSize().align(Alignment.End)) {
                                if (tarea.completada) {
                                    if (rol == "ROLE_ADMIN") {
                                        run {
                                            viewModel.uncompleteTareaA(
                                                token,
                                                tarea.name
                                            );viewModel.update()
                                        }
                                    } else {
                                        run {
                                            viewModel.uncompleteTareaN(
                                                token,
                                                tarea.name
                                            )
                                            viewModel.update()
                                        }
                                    }
                                } else {
                                    if (rol == "ROLE_ADMIN") {
                                        run {
                                            viewModel.completeTareaA(token, tarea.name)
                                            viewModel.update()
                                        }
                                    } else {
                                        run {
                                            viewModel.completeTareaN(token, tarea.name)
                                            viewModel.update()
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AddButton("Añadir tarea") {
                            navControlador.navigate(AppScreen.APITareasOperations.route)
                            viewModel.changeScreen("insertA")
                        }
                        AddButton("Cerrar sesión") {
                            navControlador.navigate(AppScreen.APIMenu.route)
                        }
                    }
                }
            }
        }
    }
}