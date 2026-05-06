package com.example.segundodoparcialseminario2parteb.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

// Barra de navegación inferior con íconos accesibles
@Composable
fun BarraNavegacionInferior(
    rutaActual: String,
    onNavegar: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = rutaActual == "explorar",
            onClick = { onNavegar("explorar") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = "Explorar países",
                    modifier = Modifier.semantics {
                        contentDescription = "Ir a explorar países"
                    }
                )
            },
            label = { Text("Explorar") }
        )
        NavigationBarItem(
            selected = rutaActual == "favoritos",
            onClick = { onNavegar("favoritos") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritos guardados",
                    modifier = Modifier.semantics {
                        contentDescription = "Ir a mis favoritos"
                    }
                )
            },
            label = { Text("Favoritos") }
        )
    }
}