package com.example.api_interfaces.app.api.dtos

data class TareaDTO(
    val name: String,
    val descripcion: String,
    val completada: Boolean,
    val usuario: String
)
