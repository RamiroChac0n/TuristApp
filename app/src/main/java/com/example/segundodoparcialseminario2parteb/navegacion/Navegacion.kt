package com.example.segundodoparcialseminario2parteb.navegacion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segundodoparcialseminario2parteb.AplicacionTurismo
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.ui.explorar.ExplorarPantalla
import com.example.segundodoparcialseminario2parteb.ui.detalle.DetallePantalla
import com.example.segundodoparcialseminario2parteb.ui.favoritos.FavoritosPantalla
import com.example.segundodoparcialseminario2parteb.ui.favoritos.FavoritosViewModel
import com.example.segundodoparcialseminario2parteb.ui.componentes.BarraNavegacionInferior

// Rutas de navegación como constantes
object Rutas {
    const val EXPLORAR = "explorar"
    const val DETALLE = "detalle"
    const val FAVORITOS = "favoritos"
}

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    val contexto = LocalContext.current
    val aplicacion = contexto.applicationContext as AplicacionTurismo
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    // País seleccionado para la pantalla de detalle
    var paisSeleccionado by remember { mutableStateOf<PaisDto?>(null) }

    val favoritosViewModel: FavoritosViewModel = viewModel(
        factory = FavoritosViewModel.crearFactory(aplicacion.contenedor.repositorioTurismo)
    )

    Scaffold(
        bottomBar = {
            // Solo mostramos la barra en las pantallas principales (Explorar y Favoritos)
            if (rutaActual == Rutas.EXPLORAR || rutaActual == Rutas.FAVORITOS) {
                BarraNavegacionInferior(
                    rutaActual = rutaActual ?: Rutas.EXPLORAR,
                    onNavegar = { ruta ->
                        navController.navigate(ruta) {
                            // Al navegar a una pestaña, volvemos a la raíz de la misma para evitar una pila infinita
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Evitamos múltiples copias del mismo destino
                            launchSingleTop = true
                            // Restauramos el estado si ya existía
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Rutas.EXPLORAR,
            modifier = Modifier.padding(paddingValues)
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
}