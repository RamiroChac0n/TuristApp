package com.example.segundodoparcialseminario2parteb.ui.explorar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segundodoparcialseminario2parteb.AplicacionTurismo
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.ui.componentes.TarjetaPais

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarPantalla(
    onPaisSeleccionado: (PaisDto) -> Unit,
    aplicacion: AplicacionTurismo
) {
    val viewModel: ExplorarViewModel = viewModel(
        factory = ExplorarViewModel.crearFactory(aplicacion.contenedor.repositorioTurismo)
    )

    val estado by viewModel.estadoUi.collectAsState()
    var textoBusqueda by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorar países") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
        // Se eliminó bottomBar de aquí para evitar la duplicidad
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = {
                    textoBusqueda = it
                    viewModel.buscarPais(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = { Text("Buscar país...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )

            when (val estadoActual = estado) {
                is EstadoExplorar.Cargando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is EstadoExplorar.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = estadoActual.mensaje, color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.cargarPaises() }) { Text("Reintentar") }
                        }
                    }
                }
                is EstadoExplorar.Exito -> {
                    AnimatedVisibility(visible = true) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(estadoActual.paises) { pais ->
                                TarjetaPais(pais = pais, onClick = { onPaisSeleccionado(pais) })
                            }
                        }
                    }
                }
            }
        }
    }
}