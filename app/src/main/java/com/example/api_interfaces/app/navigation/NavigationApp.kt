package com.example.api_interfaces.app.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_interfaces.app.MyViewModel
import com.example.api_interfaces.app.screens.APIMenu
import com.example.api_interfaces.app.screens.APITareas
import com.example.api_interfaces.app.screens.APIUser
import com.example.api_interfaces.app.screens.MainScreen
import com.example.api_interfaces.app.screens.Portada

@Composable
fun AppNavigation(modifier: Modifier, viewModel: MyViewModel) {
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = AppScreen.Portada.route){
        composable(AppScreen.Portada.route) {
            Portada(navControlador,modifier,viewModel)
        }
        composable(AppScreen.MainScreen.route) {
            MainScreen(navControlador,modifier,viewModel)
        }
        composable(AppScreen.APIMenu.route) {
            APIMenu(navControlador,modifier,viewModel)
        }
        composable(AppScreen.APIUser.route) {
            APIUser(navControlador,modifier,viewModel)
        }
        composable(AppScreen.APITareas.route){
            APITareas(navControlador,modifier,viewModel)
        }

    }
}