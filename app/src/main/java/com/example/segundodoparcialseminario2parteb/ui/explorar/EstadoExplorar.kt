package com.example.segundodoparcialseminario2parteb.ui.explorar

import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto

// Estados posibles de la pantalla de exploración
sealed interface EstadoExplorar {
    // Cargando datos desde la API
    object Cargando : EstadoExplorar

    // Datos cargados correctamente
    data class Exito(val paises: List<PaisDto>) : EstadoExplorar

    // Error al cargar los datos
    data class Error(val mensaje: String) : EstadoExplorar
}