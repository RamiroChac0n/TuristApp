package com.example.segundodoparcialseminario2parteb.ui.favoritos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.segundodoparcialseminario2parteb.datos.local.LugarTuristico
import com.example.segundodoparcialseminario2parteb.ui.tema.TemaAplicacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosPantalla(
    onRegresar: () -> Unit,
    favoritosViewModel: FavoritosViewModel
) {
    val favoritos by favoritosViewModel.favoritos.collectAsState()
    FavoritosContenido(
        favoritos = favoritos,
        onRegresar = onRegresar,
        onEliminar = { favoritosViewModel.eliminarFavorito(it) },
        onActualizar = { lugar, nuevaDesc -> 
            favoritosViewModel.actualizarFavorito(lugar.copy(descripcion = nuevaDesc))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosContenido(
    favoritos: List<LugarTuristico>,
    onRegresar: () -> Unit,
    onEliminar: (LugarTuristico) -> Unit,
    onActualizar: (LugarTuristico, String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis favoritos") },
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (favoritos.isEmpty()) {
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
                    Text(text = "Aún no tienes favoritos guardados", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = favoritos, key = { it.id }) { lugar ->
                        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                            TarjetaFavorito(
                                lugar = lugar,
                                onEliminar = { onEliminar(lugar) },
                                onActualizar = { nuevaDesc -> onActualizar(lugar, nuevaDesc) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaFavorito(
    lugar: LugarTuristico,
    onEliminar: () -> Unit,
    onActualizar: (String) -> Unit
) {
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var textoEdicion by remember { mutableStateOf(lugar.descripcion) }

    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(3.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(110.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(lugar.urlBandera).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(100.dp).fillMaxHeight()
            )

            Column(modifier = Modifier.weight(1f).padding(12.dp)) {
                Text(text = lugar.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = lugar.descripcion, style = MaterialTheme.typography.bodySmall, maxLines = 2)
            }

            Column {
                IconButton(onClick = { mostrarDialogoEditar = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { mostrarDialogoEliminar = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    if (mostrarDialogoEditar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEditar = false },
            title = { Text("Editar descripción") },
            text = {
                OutlinedTextField(
                    value = textoEdicion,
                    onValueChange = { textoEdicion = it },
                    label = { Text("Notas sobre este lugar") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    onActualizar(textoEdicion)
                    mostrarDialogoEditar = false
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogoEditar = false }) { Text("Cancelar") } }
        )
    }

    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("Eliminar") },
            text = { Text("¿Quitar ${lugar.nombre} de favoritos?") },
            confirmButton = {
                TextButton(onClick = { onEliminar(); mostrarDialogoEliminar = false }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogoEliminar = false }) { Text("Cancelar") } }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoritos() {
    TemaAplicacion {
        val listaFicticia = listOf(
            LugarTuristico(1, "Andorra", "Andorra la Vella", "Europe", "Un país pequeño", ""),
            LugarTuristico(2, "Colombia", "Bogotá", "Americas", "Tierra del café", "")
        )
        FavoritosContenido(
            favoritos = listaFicticia,
            onRegresar = {},
            onEliminar = {},
            onActualizar = { _, _ -> }
        )
    }
}