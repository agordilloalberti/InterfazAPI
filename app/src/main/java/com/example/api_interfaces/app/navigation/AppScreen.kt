package com.example.api_interfaces.app.navigation

open class AppScreen(val route: String) {
    data object Portada: AppScreen("Portada")
    data object MainScreen: AppScreen("MainScreen")
    data object APIMenu: AppScreen("APIMenu")
    data object APIUser: AppScreen("APIUser")
    data object APITareas : AppScreen("APITareas")
}