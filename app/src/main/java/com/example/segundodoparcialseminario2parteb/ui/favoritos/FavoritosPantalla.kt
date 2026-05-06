package com.example.segundodoparcialseminario2parteb.ui.favoritos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.segundodoparcialseminario2parteb.datos.local.LugarTuristico

// Pantalla que muestra la lista de destinos guardados como favoritos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosPantalla(
    onRegresar: () -> Unit,
    favoritosViewModel: FavoritosViewModel
) {
    val favoritos by favoritosViewModel.favoritos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis favoritos") },
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favoritos.isEmpty()) {
                // Mensaje cuando no hay favoritos guardados
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aún no tienes favoritos guardados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                // Lista desplazable de favoritos
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = favoritos,
                        key = { it.id }   // Key estable para animaciones de lista
                    ) { lugar ->
                        // Cada item aparece con animación de entrada
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut()
                        ) {
                            TarjetaFavorito(
                                lugar = lugar,
                                onEliminar = { favoritosViewModel.eliminarFavorito(lugar) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Tarjeta individual de un lugar favorito con opción de eliminar
@Composable
fun TarjetaFavorito(
    lugar: LugarTuristico,
    onEliminar: () -> Unit
) {
    // Estado para mostrar el diálogo de confirmación antes de eliminar
    var mostrarDialogo by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Miniatura de la bandera
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(lugar.urlBandera)
                    .crossfade(true)
                    .build(),
                contentDescription = "Bandera de ${lugar.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            )

            // Información del lugar
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = lugar.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = lugar.capital,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = lugar.region,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Botón para eliminar el favorito
            IconButton(
                onClick = { mostrarDialogo = true },
                modifier = Modifier.semantics {
                    contentDescription = "Eliminar ${lugar.nombre} de favoritos"
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Diálogo de confirmación antes de eliminar
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar favorito") },
            text = { Text("¿Deseas eliminar ${lugar.nombre} de tus favoritos?") },
            confirmButton = {
                TextButton(onClick = {
                    onEliminar()
                    mostrarDialogo = false
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}