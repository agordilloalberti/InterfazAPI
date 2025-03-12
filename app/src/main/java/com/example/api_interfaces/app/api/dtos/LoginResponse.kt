package com.example.api_interfaces.app.api.dtos

data class LoginResponse(
    var token: String,
    //TODO: Make the api return the role of the user logged
    val roles: String,
    var status:String,
    var message: List<String>)
