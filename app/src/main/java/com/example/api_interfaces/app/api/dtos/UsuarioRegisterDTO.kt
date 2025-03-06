package com.example.api_interfaces.app.api.dtos

import com.example.api_interfaces.app.api.Direccion

data class UsuarioRegisterDTO(
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val name : String,
    val surname : String,
    val rol : String?,
    val direccion: Direccion
)
