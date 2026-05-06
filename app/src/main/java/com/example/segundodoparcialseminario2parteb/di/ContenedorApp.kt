package com.example.segundodoparcialseminario2parteb.di

import android.content.Context
import com.example.segundodoparcialseminario2parteb.datos.local.BaseDeDatos
import com.example.segundodoparcialseminario2parteb.datos.remoto.ServicioApi
import com.example.segundodoparcialseminario2parteb.datos.repositorio.RepositorioTurismo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Contenedor de dependencias manual (sin Hilt)
class ContenedorApp(context: Context) {

    // Cliente HTTP con tiempos de espera personalizados para mejorar la estabilidad
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Tiempo para establecer conexión
        .readTimeout(30, TimeUnit.SECONDS)    // Tiempo para recibir datos
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Instancia de Retrofit configurada con el cliente HTTP
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .client(okHttpClient)
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