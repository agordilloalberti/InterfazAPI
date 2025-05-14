package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.AddPlainText
import com.example.api_interfaces.app.AddTextField
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppScreen

/**
 * Pantalla única para controllar el login y el registro, segun se elija una opción u otra se muestran mas o menos componentes
 */
@Composable
fun APIUser(navControlador: NavHostController, modifier: Modifier, viewModel: MyViewModel) {

    val username by viewModel.username.observeAsState("")
    val name by viewModel.name.observeAsState("")
    val surname by viewModel.surname.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val passwordRep by viewModel.passwordRepeat.observeAsState("")
    val calle by viewModel.calle.observeAsState("")
    val num by viewModel.num.observeAsState("")
    val muni by viewModel.muni.observeAsState("")
    val prov by viewModel.prov.observeAsState("")
    val cp by viewModel.cp.observeAsState("")
    val screen by viewModel.screen.observeAsState("")
    val registerResult by viewModel.registerResult.observeAsState("")
    val logged by viewModel.loginResult.observeAsState("")
    val dismissed by viewModel.dismissed.observeAsState(false)
    val loading by viewModel.loading.collectAsState()

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            if (screen == "login") {
                item { AddPlainText("LOGIN DE USUARIO") }
                item { AddPlainText("Nombre de usuario") }
                item {
                    AddTextField(
                        "Escriba su nombre de usuario",
                        username,
                        { viewModel.changeUser(it) })
                }
                item { AddPlainText("Contraseña") }
                item {
                    AddTextField(
                        "Escriba su contraseña",
                        password,
                        { viewModel.changePassword(it) },
                        transformation = PasswordVisualTransformation()
                    )
                }
                item { AddButton("Log in") { viewModel.loginUser(username, password) } }

                if (logged == "logged" && !dismissed) {
                    item {
                        AddAlertDialog(
                            "Login result",
                            "Login correcto, bienvenido \"$username\""
                        ) { viewModel.dismiss(); viewModel.changeLogginResult("");navControlador.navigate(AppScreen.APITareas.route)}
                    }
                } else if (logged == "errorLogin" && !dismissed) {
                    item {
                        AddAlertDialog(
                            "Login result",
                            "Login fallido\n${viewModel.error.value}"
                        ) { viewModel.dismiss(); viewModel.changeLogginResult("") }
                    }
                } else if (logged == "error" && !dismissed) {
                    item {
                        AddAlertDialog(
                            "Login result",
                            "Error interno\n${viewModel.error.value}"
                        ) { viewModel.dismiss(); viewModel.changeLogginResult("") }
                    }
                }

                item {
                    AddButton("Volver") {
                        navControlador.navigate(AppScreen.APIMenu.route)
                        viewModel.changeUser("")
                        viewModel.reset()
                    }
                }
            } else if (screen == "register") {
                item { AddPlainText("REGISTRO DE USUARIO") }
                item { AddPlainText("Nombre de usuario") }
                item {
                    AddTextField(
                        "Escriba su nombre de usuario",
                        username,
                        { viewModel.changeUser(it) })
                }
                item { AddPlainText("Contraseña") }
                item {
                    AddTextField(
                        "Escriba su contraseña",
                        password,
                        { viewModel.changePassword(it) },
                        transformation = PasswordVisualTransformation()
                    )
                }
                item { AddPlainText("Repetir contraseña") }
                item {
                    AddTextField(
                        "Repita su contraseña",
                        passwordRep,
                        { viewModel.changePassRepeat(it) },
                        transformation = PasswordVisualTransformation()
                    )
                }
                item { AddPlainText("Nombre") }
                item { AddTextField("Escriba su nombre", name, { viewModel.changeName(it) }) }
                item { AddPlainText("Apellido/s") }
                item { AddTextField("Escriba su apellido/s", surname, { viewModel.changeSurname(it) }) }
                item { AddPlainText("Calle") }
                item { AddTextField("Escriba su calle", calle, { viewModel.changeCalle(it) }) }
                item { AddPlainText("Número") }
                item { AddTextField("Escriba su numero", num, { viewModel.changeNum(it) }) }
                item { AddPlainText("Municipio") }
                item { AddTextField("Escriba su municipio", muni, { viewModel.changeMuni(it) }) }
                item { AddPlainText("Provincia") }
                item { AddTextField("Escriba su provincia", prov, { viewModel.changeProv(it) }) }
                item { AddPlainText("Código postal") }
                item { AddTextField("Escriba su código postal", cp, { viewModel.changeCP(it) }) }
                item {
                    AddButton("Registrarse") {
                        viewModel.register(
                            username,
                            password,
                            passwordRep,
                            name,
                            surname,
                            calle,
                            num,
                            prov,
                            muni,
                            cp
                        )
                    }
                }
                item {
                    AddButton("Volver") {
                        navControlador.navigate(AppScreen.APIMenu.route)
                        viewModel.changeUser("")
                        viewModel.reset()
                    }
                }

                if (registerResult == "OK" && !dismissed) {
                    item {
                        AddAlertDialog(
                            "Register result",
                            "Registro correcto, bienvenido \"$username\""
                        ) {
                            viewModel.dismiss();viewModel.changeRegisterResult("");navControlador.navigate(AppScreen.APITareas.route)
                        }
                    }
                } else if (registerResult == "Not OK" && !dismissed) {
                    item {
                        AddAlertDialog(
                            "Register result",
                            "Registro fallido\n${viewModel.error.value}"
                        ) {
                            viewModel.dismiss(); viewModel.changeRegisterResult("")
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
