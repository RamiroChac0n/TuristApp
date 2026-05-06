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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segundodoparcialseminario2parteb.AplicacionTurismo
import com.example.segundodoparcialseminario2parteb.datos.remoto.Banderas
import com.example.segundodoparcialseminario2parteb.datos.remoto.NombrePais
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.ui.componentes.TarjetaPais
import com.example.segundodoparcialseminario2parteb.ui.tema.TemaAplicacion

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
    
    ExplorarContenido(
        estado = estado,
        onPaisSeleccionado = onPaisSeleccionado,
        onBuscar = { viewModel.buscarPais(it) },
        onReintentar = { viewModel.cargarPaises() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarContenido(
    estado: EstadoExplorar,
    onPaisSeleccionado: (PaisDto) -> Unit,
    onBuscar: (String) -> Unit,
    onReintentar: () -> Unit
) {
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
                    onBuscar(it)
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

            when (estado) {
                is EstadoExplorar.Cargando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is EstadoExplorar.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = onReintentar) { Text("Reintentar") }
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
                            items(estado.paises) { pais ->
                                TarjetaPais(pais = pais, onClick = { onPaisSeleccionado(pais) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExplorar() {
    TemaAplicacion {
        val listaFicticia = listOf(
            PaisDto(NombrePais("Colombia", ""), null, "Americas", null, 0, Banderas("", ""), null, null),
            PaisDto(NombrePais("Andorra", ""), null, "Europe", null, 0, Banderas("", ""), null, null)
        )
        ExplorarContenido(
            estado = EstadoExplorar.Exito(listaFicticia),
            onPaisSeleccionado = {},
            onBuscar = {},
            onReintentar = {}
        )
    }
}