package com.example.segundodoparcialseminario2parteb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.segundodoparcialseminario2parteb.navegacion.NavegacionApp
import com.example.segundodoparcialseminario2parteb.ui.tema.TemaAplicacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemaAplicacion {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Punto de entrada de la navegación
                    NavegacionApp()
                }
            }
        }
    }
}