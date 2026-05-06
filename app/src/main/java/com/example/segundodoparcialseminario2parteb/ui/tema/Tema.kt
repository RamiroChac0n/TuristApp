package com.example.segundodoparcialseminario2parteb.ui.tema

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema de colores para modo claro
private val EsquemaModoClaro = lightColorScheme(
    primary = VerdePrimario,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = VerdeOscuro,
    secondary = AzulSecundario,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = AzulClaro,
    onSecondaryContainer = AzulOscuro,
    tertiary = DoradoTerciario,
    onTertiary = androidx.compose.ui.graphics.Color.Black,
    tertiaryContainer = DoradoClaro,
    onTertiaryContainer = DoradoOscuro,
    background = FondoClaro,
    surface = SuperficieClara,
    error = androidx.compose.ui.graphics.Color(0xFFB00020)
)

// Esquema de colores para modo oscuro
private val EsquemaModOscuro = darkColorScheme(
    primary = VerdeClaro,
    onPrimary = VerdeOscuro,
    primaryContainer = VerdePrimario,
    onPrimaryContainer = VerdeClaro,
    secondary = AzulClaro,
    onSecondary = AzulOscuro,
    secondaryContainer = AzulSecundario,
    onSecondaryContainer = AzulClaro,
    tertiary = DoradoClaro,
    onTertiary = DoradoOscuro,
    tertiaryContainer = DoradoTerciario,
    onTertiaryContainer = DoradoClaro,
    background = FondoOscuro,
    surface = SuperficieOscura,
    error = androidx.compose.ui.graphics.Color(0xFFCF6679)
)

// Tema principal de la aplicación
@Composable
fun TemaAplicacion(
    modoOscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val esquemaColores = if (modoOscuro) EsquemaModOscuro else EsquemaModoClaro

    // Sincroniza el color de la barra de estado con el tema
    val vista = LocalView.current
    if (!vista.isInEditMode) {
        SideEffect {
            val ventana = (vista.context as Activity).window
            ventana.statusBarColor = esquemaColores.primary.toArgb()
            WindowCompat.getInsetsController(ventana, vista)
                .isAppearanceLightStatusBars = !modoOscuro
        }
    }

    MaterialTheme(
        colorScheme = esquemaColores,
        typography = TipografiaApp,
        content = content
    )
}