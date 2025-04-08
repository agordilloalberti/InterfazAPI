package com.example.api_interfaces.app.screens

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    val loading by viewModel.loading.collectAsState()

    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (tareas.isNullOrEmpty()) {
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

                if (!dismissed) {
                    Log.e("ERGBYHUNEDRFUNJRTYUHNJ","ERROR APITAREAS")
                    AddAlertDialog(
                        "Advertencia",
                        "No existen tareas para este usuario"
                    ) { viewModel.dismiss() }
                }
            }
        } else {
            LazyColumn(modifier.fillMaxSize().background(Color.Black)) {

                items(tareas!!) { tarea ->
                    val nombre = tarea.name
                    val estado = if (tarea.completada) "Descompletar" else "Completar"
                    val user = tarea.usuario
                    val desc = tarea.descripcion
                    val txt = "Nombre de la tarea:\n\"$nombre\"\n\n" +
                            "Asignada a:\n\"$user\"\n\n" +
                            "Descripción:\n\"$desc\"\n\n" +
                            "Estado:\n$estado"

                    Row(Modifier.fillMaxWidth().border(1.dp,Color.White).padding(1.dp)) {
                        Text(txt,Modifier.wrapContentSize().weight(2f),Color.White)
                        Column(Modifier.wrapContentSize().weight(1f)) {

                            AddButton("Editar",Modifier.wrapContentSize().align(Alignment.End)) {
                                navControlador.navigate(AppScreen.APITareasOperations.route)
                                viewModel.changeScreen(if (rol == "ROLE_ADMIN") "updateA" else "updateN")
                            }

                            AddButton("Borrar",Modifier.wrapContentSize().align(Alignment.End)) {
                                if (rol == "ROLE_ADMIN") {
                                    run {
                                        viewModel.deleteTareaA(token, tarea.name)
                                    }
                                } else {
                                    run {
                                        viewModel.deleteTareaN(token, tarea.name)
                                    }
                                }
                            }

                            AddButton(estado,Modifier.wrapContentSize().align(Alignment.End)) {
                                if (tarea.completada) {
                                    if (rol == "ROLE_ADMIN") {
                                        run {
                                            viewModel.uncompleteTareaA(
                                                token,
                                                tarea.name)
                                        }
                                    } else {
                                        run {
                                            viewModel.uncompleteTareaN(
                                                token,
                                                tarea.name
                                            )
                                        }
                                    }
                                } else {
                                    if (rol == "ROLE_ADMIN") {
                                        run {
                                            viewModel.completeTareaA(token, tarea.name)
                                        }
                                    } else {
                                        run {
                                            viewModel.completeTareaN(token, tarea.name)
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
                            if (rol == "ROLE_ADMIN") {
                                viewModel.changeScreen("insertA")
                            }else{
                                viewModel.changeScreen("insertN")
                            }
                        }
                        AddButton("Cerrar sesión") {
                            navControlador.navigate(AppScreen.APIMenu.route)
                        }
                    }
                }
            }
        }
        if (loading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}