package com.example.api_interfaces.app.api

import com.example.api_interfaces.app.api.dtos.LoginResponse
import com.example.api_interfaces.app.api.dtos.LoginUsuarioDTO
import com.example.api_interfaces.app.api.dtos.NormalResponse
import com.example.api_interfaces.app.api.dtos.TareaAddADTO
import com.example.api_interfaces.app.api.dtos.TareaAddSDTO
import com.example.api_interfaces.app.api.dtos.UsuarioRegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIService {

    //POST

    @POST("Usuarios/login")
    suspend fun login(
        @Body usuario: LoginUsuarioDTO
    ): Response<LoginResponse>

    @POST("Usuarios/register")
    suspend fun register(
        @Body usuario: UsuarioRegisterDTO
    ): Response<NormalResponse>

    @POST("Tareas/insert")
    suspend fun insertN(
        @Body tarea: TareaAddSDTO,
        @Header("Authorization") token: String
    ): Response<NormalResponse>

    @POST("TareasAdmin/add")
    suspend fun insertA(
        @Body tarea: TareaAddADTO,
        @Header("Authorization") token: String
    ): Response<NormalResponse>


    //GET

    @GET("Tareas/get")
    suspend fun getN(
        @Header("Authorization") token: String
    ): Response<NormalResponse>

    @GET("TareasAdmin/{username}")
    suspend fun getOther(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<NormalResponse>

    @GET("TareasAdmin/get")
    suspend fun getSelf(
        @Header("Authorization") token: String
    ): Response<NormalResponse>


    //PUT

    @PUT("Tareas/{tarea}")
    suspend fun updateN(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String,
        @Body tareaAdd: TareaAddSDTO
    ): Response<NormalResponse>

    @PUT("TareasAdmin/{tarea}")
    suspend fun updateA(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String,
        @Body tareaAdd: TareaAddADTO
    ): Response<NormalResponse>

    @PUT("Tareas/complete/{tarea}")
    suspend fun completeN(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String
    ): Response<NormalResponse>

    @PUT("TareasAdmin/complete/{tarea}")
    suspend fun completeA(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String
    ): Response<NormalResponse>

    @PUT("Tareas/uncomplete/{tarea}")
    suspend fun uncompleteN(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String
    ): Response<NormalResponse>

    @PUT("TareasAdmin/uncomplete/{tarea}")
    suspend fun uncompleteA(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String
    ): Response<NormalResponse>


    //DELETE

    @DELETE("Tareas/{tarea}")
    suspend fun deleteN(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String
    ): Response<NormalResponse>

    @DELETE("TareasAdmin/{tarea}")
    suspend fun deleteA(
        @Header("Authorization") token: String,
        @Path("tarea") tarea: String
    ): Response<NormalResponse>
}