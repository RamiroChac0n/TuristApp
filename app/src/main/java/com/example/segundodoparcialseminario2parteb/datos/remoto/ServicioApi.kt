package com.example.segundodoparcialseminario2parteb.datos.remoto

import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz de Retrofit que define los endpoints de la API de países
interface ServicioApi {

    // Obtiene todos los países con los campos necesarios para la app
    @GET("all?fields=name,capital,region,subregion,population,flags,languages,area")
    suspend fun obtenerPaises(): List<PaisDto>

    // Obtiene países filtrados por región
    @GET("region/{region}?fields=name,capital,region,subregion,population,flags,languages,area")
    suspend fun obtenerPaisesPorRegion(
        @Path("region") region: String
    ): List<PaisDto>

    // Busca países por nombre
    @GET("name/{nombre}?fields=name,capital,region,subregion,population,flags,languages,area")
    suspend fun buscarPaises(
        @Path("nombre") nombre: String
    ): List<PaisDto>
}