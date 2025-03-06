package com.example.api_interfaces.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.example.api_interfaces.app.AddAlertDialog
import com.example.api_interfaces.app.AddButton
import com.example.api_interfaces.app.AddTextField
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.navigation.AppScreen

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
    val token by viewModel.token.observeAsState("")
    val logged by viewModel.loginResult.observeAsState("")
    val dismissed by viewModel.dismissed.observeAsState(false)

    Column(modifier.fillMaxSize().background(Color.Black)) {
        AddTextField("Escriba su nombre de usuario", username, { viewModel.changeUser(it) })
        AddTextField("Escriba su contraseña", password, { viewModel.changePassword(it) }, transformation = PasswordVisualTransformation())
        AddButton("Logg in") { viewModel.loginUser(username, password) }
        if (logged == "logged" && !dismissed) {
            AddAlertDialog("Login result", "Login correcto: \ntoken:\n$token") { viewModel.dismiss();viewModel.changeLogginResult("") }
        } else if (logged == "errorLogin" && !dismissed) {
            AddAlertDialog("Login result", "Login fallido") { viewModel.dismiss();viewModel.changeLogginResult("") }
        } else if (logged == "error" && !dismissed) {
            AddAlertDialog("Login result", "Error interno") { viewModel.dismiss();viewModel.changeLogginResult("") }
        }
        AddButton("Volver") {navControlador.navigate(AppScreen.APIMenu.route);viewModel.changeUser("");viewModel.reset()}
    }
}