package com.example.segundodoparcialseminario2parteb.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto

// Tarjeta que muestra la bandera y nombre de un país en la cuadrícula
@Composable
fun TarjetaPais(
    pais: PaisDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Imagen de la bandera del país
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pais.banderas.png)
                    .crossfade(true)
                    .build(),
                contentDescription = "Bandera de ${pais.nombre.comun}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                // Nombre del país
                Text(
                    text = pais.nombre.comun,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                // Región
                Text(
                    text = pais.region,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}