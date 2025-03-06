package com.example.api_interfaces.app.api

data class Direccion(
    val calle: String,
    val num: String,
    val municipio: String,
    val provincia: String,
    val cp: String
)
{
    fun isEmpty(): Boolean{
        return calle.isBlank() || num.isBlank() || municipio.isBlank() || provincia.isBlank()|| cp.isBlank()
    }
}