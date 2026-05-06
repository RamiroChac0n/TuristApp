package com.example.segundodoparcialseminario2parteb.datos.repositorio

import com.example.segundodoparcialseminario2parteb.datos.local.LugarTuristico
import com.example.segundodoparcialseminario2parteb.datos.local.LugarTuristicoDao
import com.example.segundodoparcialseminario2parteb.datos.remoto.ServicioApi
import kotlinx.coroutines.flow.Flow

// Repositorio que centraliza el acceso a datos locales (Room) y remotos (API)
class RepositorioTurismo(
    private val dao: LugarTuristicoDao,
    private val api: ServicioApi
) {
    // --- Operaciones locales (Room) ---

    // Retorna el flujo de favoritos guardados
    val favoritos: Flow<List<LugarTuristico>> = dao.obtenerTodos()

    // Guarda un lugar como favorito
    suspend fun guardarFavorito(lugar: LugarTuristico) {
        dao.insertar(lugar)
    }

    // Elimina un lugar de favoritos
    suspend fun eliminarFavorito(lugar: LugarTuristico) {
        dao.eliminar(lugar)
    }

    // Actualiza los datos de un favorito
    suspend fun actualizarFavorito(lugar: LugarTuristico) {
        dao.actualizar(lugar)
    }

    // --- Operaciones remotas (API) ---

    // Obtiene la lista de países desde la API
    suspend fun obtenerPaises() = api.obtenerPaises()
}