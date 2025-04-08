package com.example.api_interfaces.app.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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


/**
 * Esta es la pantalla dedicada a las operaciones de las tareas
 */
@Composable
fun APITareasOperations(
    navControlador: NavHostController,
    modifier: Modifier,
    viewModel: MyViewModel
) {

    //Estas variables cotrolan:

    //Los componentes a mostrar
    val comps by viewModel.comps.observeAsState(listOf(""))

    //El nombre de la tarea
    val tName by viewModel.tName.observeAsState("")

    //El nuevo nombre de la tarea
    val tNName by viewModel.tNName.observeAsState("")

    //La descripción de la tarea
    val desc by viewModel.desc.observeAsState("")

    //El nombre de usuario
    val username by viewModel.tUsername.observeAsState("")

    //La operación a realizar
    val screen by viewModel.screen.observeAsState("")

    //El token de autenticación
    val token by viewModel.token.observeAsState("")

    //El resultado de la operación
    val opRes by viewModel.opRes.observeAsState(false)

    //El mensaje duevuelto
    val msg by viewModel.msg.observeAsState("")

    //El mensaje de error duevuelto
    val error by viewModel.error.observeAsState("")

    //El estado de los dialogos de alerta
    val dismissed by viewModel.dismissed.observeAsState(false)

    LazyColumn(modifier
        .fillMaxSize()
        .background(Color.Black)) {
        item { AddPlainText("Operación: $screen")}

        //Los dialogos se muestran si: no hay mensaje, es decir, no se ha relizado la operación, el resultado de la operación,
        //para saber cual mostrar y el dismiss, para ocultarlos una vez se pulse el boton.
        if (error.isNotBlank() && !opRes && !dismissed){

            Log.e("ERGBYHUNEDRFUNJRTYUHNJ","ERROR OP ERROR")

            item {AddAlertDialog("Error","error: $error")
                {
                    viewModel.dismiss()
                    viewModel.clearError()
                }
            }
        }else if(msg.isNotBlank() && opRes && !dismissed){

            Log.e("ERGBYHUNEDRFUNJRTYUHNJ","RESULTADO")

            item {AddAlertDialog("Result","Operación: $screen realizada con exito\nRespuesta: $msg")
                {
                    viewModel.dismiss()
                    viewModel.clearMsg()
                    viewModel.resetOP()
                    navControlador.navigate(AppScreen.APITareas.route)
                }
            }
        }


        //Cada campo de texto y su pequqña label se muestra si son necesarios

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
            Row {
                //Este boton, dependiendo de la operación solicitada, ejecuta una acción u otra
                AddButton("Proceder") {
                    when (screen) {
                        "insertN" -> viewModel.insertTareaN(TareaAddNDTO(tName, desc), token)
                        "insertA" -> viewModel.insertTareaA(
                            TareaAddADTO(tName, desc, username),
                            token
                        )

                        "updateN" -> viewModel.updateTareaN(
                            token,
                            tName,
                            TareaAddNDTO(tNName, desc)
                        )

                        "updateA" -> viewModel.updateTareaA(
                            token,
                            tName,
                            TareaAddADTO(tNName, desc, username)
                        )
                    }
                }

                //Boton que devuelve a la pantalla anterior
                AddButton("Volver") {
                    viewModel.resetOP()
                    navControlador.navigate(AppScreen.APITareas.route)
                }
            }
        }
    }
}