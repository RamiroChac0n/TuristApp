package com.example.segundodoparcialseminario2parteb.ui.detalle

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.segundodoparcialseminario2parteb.AplicacionTurismo
import com.example.segundodoparcialseminario2parteb.datos.local.LugarTuristico
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.ui.favoritos.FavoritosViewModel

// Pantalla de detalle de un país con opción de guardar como favorito
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePantalla(
    pais: PaisDto,
    onRegresar: () -> Unit,
    aplicacion: AplicacionTurismo,
    favoritosViewModel: FavoritosViewModel
) {
    // Controla si el ícono muestra favorito activo o no
    var esFavorito by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pais.nombre.comun) },
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                // Botón para guardar o quitar de favoritos
                actions = {
                    IconButton(onClick = {
                        esFavorito = !esFavorito
                        if (esFavorito) {
                            favoritosViewModel.guardarFavorito(pais.aLugarTuristico())
                        } else {
                            favoritosViewModel.eliminarFavoritoPorPais(pais.nombre.comun)
                        }
                    }) {
                        Icon(
                            imageVector = if (esFavorito) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = if (esFavorito) "Quitar de favoritos"
                            else "Guardar en favoritos",
                            tint = if (esFavorito) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .animateContentSize()   // Animación suave al cambiar contenido
        ) {
            // Bandera grande en la parte superior
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pais.banderas.png)
                    .crossfade(true)
                    .build(),
                contentDescription = "Bandera de ${pais.nombre.comun}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Sección de información del país
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = pais.nombre.oficial,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Filas de información clave
                FilaInfo("Capital", pais.capital?.firstOrNull() ?: "N/D")
                FilaInfo("Región", pais.region)
                FilaInfo("Subregión", pais.subregion ?: "N/D")
                FilaInfo("Población", "%,d".format(pais.poblacion))
                FilaInfo("Área", "${pais.area?.let { "%,.0f km²".format(it) } ?: "N/D"}")
                FilaInfo(
                    "Idiomas",
                    pais.idiomas?.values?.joinToString(", ") ?: "N/D"
                )
            }
        }
    }
}

// Componente reutilizable para mostrar un par etiqueta-valor
@Composable
private fun FilaInfo(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    HorizontalDivider(thickness = 0.5.dp)
}

// Función de extensión para convertir un PaisDto en LugarTuristico (para guardar en Room)
fun PaisDto.aLugarTuristico(): LugarTuristico {
    return LugarTuristico(
        nombre = this.nombre.comun,
        capital = this.capital?.firstOrNull() ?: "N/D",
        region = this.region,
        descripcion = "Nombre oficial: ${this.nombre.oficial}",
        urlBandera = this.banderas.png
    )
}