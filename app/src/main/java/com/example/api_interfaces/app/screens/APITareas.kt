package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppScreen

@Composable
fun APITareas(navControlador: NavController, modifier: Modifier, viewModel: MyViewModel) {

    viewModel.update()

    val usuario by viewModel.username.observeAsState("")
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
                Row(Modifier.align(Alignment.Center)) {
                    if (rol == "ROLE_ADMIN") {
                        AddButton(
                            "Añadir tarea",
                        ) {
                            navControlador.navigate(AppScreen.APITareasOperations.route)
                            viewModel.changeScreen("insertA")
                            viewModel.clearMsg()
                        }
                    } else {
                        AddButton(
                            "Añadir tarea",
                        ) {
                            navControlador.navigate(AppScreen.APITareasOperations.route)
                            viewModel.changeScreen("insertN")
                            viewModel.clearMsg()
                        }
                    }
                    AddButton("Cerrar sesión") {
                        navControlador.navigate(AppScreen.APIMenu.route)
                        viewModel.reset()
                        viewModel.resetOP()
                        viewModel.clearMsg()
                        viewModel.clearError()
                    }

                    if (!dismissed) {
                        AddAlertDialog(
                            "Advertencia",
                            "No existen tareas"
                        ) { viewModel.dismiss() }
                    }
                }
            }
        } else {
            LazyColumn(modifier.fillMaxSize().background(Color.Black)) {

                items(tareas!!) { tarea ->
                    if (rol == "ROLE_ADMIN" || tarea.usuario == usuario) {
                        val nombre = tarea.name
                        val estado = if (!tarea.completada) "Incompleta" else "Completada"
                        val txtBoton = if (tarea.completada) "Descompletar" else "Completar"
                        val user = tarea.usuario
                        val desc = tarea.descripcion
                        val txt = "Nombre de la tarea:  \"$nombre\"\n\n" +
                                "Asignada a:  \"$user\"\n\n" +
                                "Descripción:  \"$desc\"\n\n" +
                                "Estado:  $estado"



                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.White)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    txt,
                                    Modifier,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Row(
                                    Modifier
                                ) {
                                    AddButton("Editar", Modifier, txtSize = 12) {
                                        navControlador.navigate(AppScreen.APITareasOperations.route)
                                        viewModel.changeTName(nombre)
                                        viewModel.changeScreen(if (rol == "ROLE_ADMIN") "updateA" else "updateN")
                                    }

                                    AddButton("Borrar", Modifier, txtSize = 12) {
                                        if (rol == "ROLE_ADMIN") viewModel.deleteTareaA(
                                            token,
                                            tarea.name
                                        )
                                        else viewModel.deleteTareaN(token, tarea.name)
                                    }

                                    AddButton(txtBoton, Modifier, txtSize = 12) {
                                        if (tarea.completada) {
                                            if (rol == "ROLE_ADMIN") viewModel.uncompleteTareaA(
                                                token,
                                                tarea.name
                                            )
                                            else viewModel.uncompleteTareaN(token, tarea.name)
                                        } else {
                                            if (rol == "ROLE_ADMIN") viewModel.completeTareaA(
                                                token,
                                                tarea.name
                                            )
                                            else viewModel.completeTareaN(token, tarea.name)
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
                            if (rol == "ROLE_ADMIN") {
                                viewModel.changeScreen("insertA")
                            }else{
                                viewModel.changeScreen("insertN")
                            }
                            navControlador.navigate(AppScreen.APITareasOperations.route)
                        }
                        AddButton("Cerrar sesión") {
                            viewModel.reset()
                            viewModel.resetOP()
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
                Text("Cargando...",color = Color.White)
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}