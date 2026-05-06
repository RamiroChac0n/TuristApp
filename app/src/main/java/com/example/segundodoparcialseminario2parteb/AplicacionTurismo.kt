package com.example.segundodoparcialseminario2parteb

import android.app.Application
import com.example.segundodoparcialseminario2parteb.di.ContenedorApp

// Clase Application personalizada para inicializar el contenedor de dependencias
class AplicacionTurismo : Application() {

    // Contenedor accesible desde cualquier ViewModel
    lateinit var contenedor: ContenedorApp

    override fun onCreate() {
        super.onCreate()
        contenedor = ContenedorApp(this)
    }
}