package com.example.api_interfaces.app.api

data class LoginResponse(
    var token: String,
    var status:String,
    var message: List<String>)
