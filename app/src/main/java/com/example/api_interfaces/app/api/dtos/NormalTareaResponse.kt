package com.example.api_interfaces.app.api.dtos

data class NormalTareaResponse(
    val status : String,
    val message: List<String>,
    val tarea: TareaDTO
)