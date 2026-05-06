package com.example.segundodoparcialseminario2parteb.ui.explorar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.datos.repositorio.RepositorioTurismo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel que gestiona la lógica de la pantalla de exploración
class ExplorarViewModel(
    private val repositorio: RepositorioTurismo
) : ViewModel() {

    // Estado observable de la pantalla
    private val _estadoUi = MutableStateFlow<EstadoExplorar>(EstadoExplorar.Cargando)
    val estadoUi: StateFlow<EstadoExplorar> = _estadoUi.asStateFlow()

    // Lista completa sin filtrar para poder buscar localmente
    private var listaPaises: List<PaisDto> = emptyList()

    // Carga los países al iniciar el ViewModel
    init {
        cargarPaises()
    }

    // Llama a la API y actualiza el estado según el resultado
    fun cargarPaises() {
        viewModelScope.launch {
            _estadoUi.value = EstadoExplorar.Cargando
            try {
                listaPaises = repositorio.obtenerPaises()
                    .sortedBy { it.nombre.comun }
                _estadoUi.value = EstadoExplorar.Exito(listaPaises)
            } catch (e: Exception) {
                _estadoUi.value = EstadoExplorar.Error(
                    e.message ?: "Error al conectar con el servidor"
                )
            }
        }
    }

    // Filtra la lista localmente por nombre sin hacer otra llamada a la API
    fun buscarPais(consulta: String) {
        val resultado = if (consulta.isBlank()) {
            listaPaises
        } else {
            listaPaises.filter {
                it.nombre.comun.contains(consulta, ignoreCase = true)
            }
        }
        _estadoUi.value = EstadoExplorar.Exito(resultado)
    }

    // Factory para crear el ViewModel con dependencias
    companion object {
        fun crearFactory(repositorio: RepositorioTurismo): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExplorarViewModel(repositorio) as T
                }
            }
        }
    }
}