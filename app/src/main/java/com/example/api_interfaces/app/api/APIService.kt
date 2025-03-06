package com.example.api_interfaces.app.api

import com.example.api_interfaces.app.api.dtos.LoginUsuarioDTO
import com.example.api_interfaces.app.api.dtos.UsuarioRegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("Usuarios/login")
    suspend fun login(@Body usuario: LoginUsuarioDTO):Response<LoginResponse>

    @POST("Usuarios/register")
    suspend fun register(@Body usuario: UsuarioRegisterDTO):Response<RegisterResponse>
}