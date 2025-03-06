package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.api_interfaces.app.api.dtos.TareaAddSDTO
import com.example.api_interfaces.app.navigation.AppScreen

@Composable
fun APITareasOperations(
    navControlador: NavHostController,
    modifier: Modifier,
    viewModel: MyViewModel
) {
    val comps by viewModel.comps.observeAsState()
    val tName by viewModel.tName.observeAsState("")
    val tNName by viewModel.tNName.observeAsState("")
    val desc by viewModel.desc.observeAsState("")
    val username by viewModel.tUsername.observeAsState("")
    val screen by viewModel.screen.observeAsState("")
    val token by viewModel.token.observeAsState("")
    val opRes by viewModel.opRes.observeAsState(listOf())
    val error by viewModel.error.observeAsState("")
    val dismissed by viewModel.dismissed.observeAsState(false)

    Column(modifier
        .fillMaxSize()
        .background(Color.Black)) {
        AddPlainText("Operación: $screen")

        if (opRes[0] =="Error" && !dismissed){
            AddAlertDialog("Error",error) {viewModel.dismiss();viewModel.changeOpRes("")}
        }else if(opRes.contains("OK")){
            AddAlertDialog("Result",opRes.toString()) {viewModel.dismiss();viewModel.changeOpRes("")}
        }

        if (comps!!.contains("name")) {
            AddPlainText("Nombre de la tarea")
            AddTextField("Nombre", tName, { viewModel.changeTName(it) })
        }

        if (comps!!.contains("newName")) {
            AddPlainText("Nuevo nombre de la tarea")
            AddTextField("Nuevo nombre", tNName, { viewModel.changeTNName(it) })
        }

        if (comps!!.contains("descrip")) {
            AddPlainText("Descripción de la tarea")
            AddTextField("Descripción", desc, { viewModel.changeDes(it) })
        }

        if (comps!!.contains("username")) {
            AddPlainText("Nombre del usuario\nSi se deja vacio se usara el nombre del usuario actual")
            AddTextField("Username", username, { viewModel.changeUsername(it) })
        }

        AddButton("Confirmar operación $screen")
        {
            if (screen=="insertN") {
                viewModel.insertTareaN(TareaAddSDTO(tName,desc),token)
            }else if (screen=="insertA"){
                viewModel.insertTareaA(TareaAddADTO(tName,desc,username),token)
            }else if(screen=="getN"){
                viewModel.getTareaN(token)
            }else if (screen=="getA" && username.isBlank()){
                viewModel.getTareaA(token)
            }else if (screen=="getA") {
                viewModel.getTareaOtro(token, username)
            }else if (screen=="updateN"){
                viewModel.updateTareaN(token,tName,TareaAddSDTO(tNName,desc))
            }else if(screen=="updateA"){
                viewModel.updateTareaA(token,tName,(TareaAddADTO(tNName,desc,username)))
            }else if (screen=="completeN"){
                viewModel.completeTareaN(token,tName)
            }else if (screen=="completeA"){
                viewModel.completeTareaA(token,tName)
            }else if (screen=="uncompleteN"){
                viewModel.uncompleteTareaN(token,tName)
            }else if (screen=="uncompleteA") {
                viewModel.uncompleteTareaA(token, tName)
            }else if(screen=="deleteN"){
                viewModel.deleteTareaN(token,tName)
            }else if (screen=="deleteA"){
                viewModel.deleteTareaA(token,tName)
            }
        }
        AddButton("Volver") {
            navControlador.navigate(AppScreen.APIMenu.route)
            viewModel.changeUser("")
            viewModel.reset()
        }
    }
}