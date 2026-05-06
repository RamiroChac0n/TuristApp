package com.example.segundodoparcialseminario2parteb.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.segundodoparcialseminario2parteb.datos.local.LugarTuristico
import com.example.segundodoparcialseminario2parteb.datos.repositorio.RepositorioTurismo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel que gestiona los lugares turísticos guardados en Room
class FavoritosViewModel(
    private val repositorio: RepositorioTurismo
) : ViewModel() {

    // Flujo de favoritos convertido a StateFlow para observarlo desde Compose
    val favoritos: StateFlow<List<LugarTuristico>> = repositorio.favoritos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    // Guarda un lugar como favorito en Room
    fun guardarFavorito(lugar: LugarTuristico) {
        viewModelScope.launch {
            repositorio.guardarFavorito(lugar)
        }
    }

    // Elimina un favorito por su objeto completo
    fun eliminarFavorito(lugar: LugarTuristico) {
        viewModelScope.launch {
            repositorio.eliminarFavorito(lugar)
        }
    }

    // Elimina un favorito buscándolo por nombre del país
    // Se usa desde la pantalla de detalle donde solo tenemos el PaisDto
    fun eliminarFavoritoPorPais(nombrePais: String) {
        viewModelScope.launch {
            val lugar = favoritos.value.find { it.nombre == nombrePais }
            lugar?.let { repositorio.eliminarFavorito(it) }
        }
    }

    // Factory para instanciar el ViewModel con su dependencia
    companion object {
        fun crearFactory(repositorio: RepositorioTurismo): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritosViewModel(repositorio) as T
                }
            }
        }
    }
}