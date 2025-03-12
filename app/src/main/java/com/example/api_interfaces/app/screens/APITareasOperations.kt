package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.AddPlainText
import com.example.api_interfaces.app.AddTextField
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.api.dtos.TareaAddADTO
import com.example.api_interfaces.app.api.dtos.TareaAddNDTO
import com.example.api_interfaces.app.navigation.AppScreen

@Composable
fun APITareasOperations(
    navControlador: NavHostController,
    modifier: Modifier,
    viewModel: MyViewModel
) {
    val comps by viewModel.comps.observeAsState(listOf(""))
    val tName by viewModel.tName.observeAsState("")
    val tNName by viewModel.tNName.observeAsState("")
    val desc by viewModel.desc.observeAsState("")
    val username by viewModel.tUsername.observeAsState("")
    val screen by viewModel.screen.observeAsState("")
    val token by viewModel.token.observeAsState("")
    val opRes by viewModel.opRes.observeAsState(false)
    val msg by viewModel.msg.observeAsState("")
    val error by viewModel.error.observeAsState("")
    val dismissed by viewModel.dismissed.observeAsState(false)

    LazyColumn(modifier
        .fillMaxSize()
        .background(Color.Black)) {
        item { AddPlainText("Operación: $screen")}

        if (error.isNotBlank() && !opRes && !dismissed){
            item {AddAlertDialog("Error","error: $error") {viewModel.dismiss();viewModel.clearError()}}
        }else if(msg.isNotBlank() && opRes && !dismissed){
            item {AddAlertDialog("Result","Operación: $screen realizada con exito\nRespuesta: $msg") {viewModel.dismiss();viewModel.clearMsg()}}
        }

        if (comps.contains("name")) {
            item{AddPlainText("Nombre de la tarea")}
            item{AddTextField("Nombre", tName, { viewModel.changeTName(it) })}
        }

        if (comps.contains("newName")) {
            item{AddPlainText("Nuevo nombre de la tarea")}
            item{AddTextField("Nuevo nombre", tNName, { viewModel.changeTNName(it) })}
        }

        if (comps.contains("descrip")) {
            item{AddPlainText("Descripción de la tarea")}
            item{AddTextField("Descripción", desc, { viewModel.changeDes(it) })}
        }

        if (comps.contains("username")) {
            item{AddPlainText("Nombre del usuario\nSi se deja vacio se usara el nombre del usuario actual")}
            item{AddTextField("Username", username, { viewModel.changeTusername(it) })}
        }

        item {
            AddButton("Proceder") {
                when (screen) {
                    "insertN" -> viewModel.insertTareaN(TareaAddNDTO(tName, desc), token)
                    "insertA" -> viewModel.insertTareaA(TareaAddADTO(tName, desc, username), token)
                    "getN" -> viewModel.getTareaN(token)
                    "getA" -> if (username.isBlank()) {
                        viewModel.getTareaA(token)
                    } else {
                        viewModel.getTareaOtro(token, username)
                    }
                    "updateN" -> viewModel.updateTareaN(token, tName, TareaAddNDTO(tNName, desc))
                    "updateA" -> viewModel.updateTareaA(token, tName, TareaAddADTO(tNName, desc, username))
                    "completeN" -> viewModel.completeTareaN(token, tName)
                    "completeA" -> viewModel.completeTareaA(token, tName)
                    "uncompleteN" -> viewModel.uncompleteTareaN(token, tName)
                    "uncompleteA" -> viewModel.uncompleteTareaA(token, tName)
                    "deleteN" -> viewModel.deleteTareaN(token, tName)
                    "deleteA" -> viewModel.deleteTareaA(token, tName)
                }
            }
        }

        item{AddButton("Volver") {
            navControlador.navigate(AppScreen.APITareasOperations.route)
            viewModel.reset()
        }}
    }
}