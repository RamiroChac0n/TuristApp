package com.example.segundodoparcialseminario2parteb.di

import android.content.Context
import com.example.segundodoparcialseminario2parteb.datos.local.BaseDeDatos
import com.example.segundodoparcialseminario2parteb.datos.remoto.ServicioApi
import com.example.segundodoparcialseminario2parteb.datos.repositorio.RepositorioTurismo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Contenedor de dependencias manual (sin Hilt)
// Se instancia una sola vez desde la Application
class ContenedorApp(context: Context) {

    // Instancia de Retrofit apuntando a la API de países
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Servicio de API generado por Retrofit
    private val servicioApi: ServicioApi = retrofit.create(ServicioApi::class.java)

    // Base de datos Room
    private val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(context)

    // Repositorio único compartido en toda la app
    val repositorioTurismo = RepositorioTurismo(
        dao = baseDeDatos.lugarDao(),
        api = servicioApi
    )
}