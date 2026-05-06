package com.example.segundodoparcialseminario2parteb.navegacion

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segundodoparcialseminario2parteb.AplicacionTurismo
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.ui.explorar.ExplorarPantalla
import com.example.segundodoparcialseminario2parteb.ui.detalle.DetallePantalla
import com.example.segundodoparcialseminario2parteb.ui.favoritos.FavoritosPantalla
import com.example.segundodoparcialseminario2parteb.ui.favoritos.FavoritosViewModel

// Rutas de navegación como constantes
object Rutas {
    const val EXPLORAR = "explorar"
    const val DETALLE = "detalle"
    const val FAVORITOS = "favoritos"
}

// Configura el grafo de navegación de la app
@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    val contexto = LocalContext.current
    val aplicacion = contexto.applicationContext as AplicacionTurismo

    // País seleccionado que se pasa a la pantalla de detalle
    var paisSeleccionado by remember { mutableStateOf<PaisDto?>(null) }

    // ViewModel de favoritos compartido entre pantallas
    val favoritosViewModel: FavoritosViewModel = viewModel(
        factory = FavoritosViewModel.crearFactory(aplicacion.contenedor.repositorioTurismo)
    )

    NavHost(
        navController = navController,
        startDestination = Rutas.EXPLORAR
    ) {
        composable(Rutas.EXPLORAR) {
            ExplorarPantalla(
                onPaisSeleccionado = { pais ->
                    paisSeleccionado = pais
                    navController.navigate(Rutas.DETALLE)
                },
                aplicacion = aplicacion
            )
        }

        composable(Rutas.DETALLE) {
            // Solo navega si hay un país seleccionado
            paisSeleccionado?.let { pais ->
                DetallePantalla(
                    pais = pais,
                    onRegresar = { navController.popBackStack() },
                    aplicacion = aplicacion,
                    favoritosViewModel = favoritosViewModel
                )
            }
        }

        composable(Rutas.FAVORITOS) {
            FavoritosPantalla(
                onRegresar = { navController.popBackStack() },
                favoritosViewModel = favoritosViewModel
            )
        }
    }
}