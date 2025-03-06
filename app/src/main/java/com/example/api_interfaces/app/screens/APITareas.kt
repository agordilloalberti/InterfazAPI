package com.example.api_interfaces.app.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.api_interfaces.app.MyViewModel

@Composable
fun APITareas(navControlador: NavHostController, modifier: Modifier, viewModel: MyViewModel) {

    if (viewModel.token.value.isNullOrBlank())
    val token by viewModel.token.observeAsState("")

}