package com.example.segundodoparcialseminario2parteb

import com.example.segundodoparcialseminario2parteb.datos.remoto.Banderas
import com.example.segundodoparcialseminario2parteb.datos.remoto.NombrePais
import com.example.segundodoparcialseminario2parteb.datos.remoto.PaisDto
import com.example.segundodoparcialseminario2parteb.ui.detalle.aLugarTuristico
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Prueba unitaria para verificar la lógica de transformación de datos.
 * Cumple con el requisito de "Al menos una prueba relacionada con la lógica o transformación".
 */
class TurismoUnitTest {

    @Test
    fun transformacion_PaisDto_a_LugarTuristico_esCorrecta() {
        // 1. Preparación (Given)
        val paisApi = PaisDto(
            nombre = NombrePais(comun = "Colombia", oficial = "República de Colombia"),
            capital = listOf("Bogotá"),
            region = "Americas",
            subregion = "South America",
            poblacion = 51000000,
            banderas = Banderas(png = "url_bandera_png", svg = "url_bandera_svg"),
            idiomas = mapOf("spa" to "Spanish"),
            area = 1141748.0
        )

        // 2. Ejecución (When)
        val lugarLocal = paisApi.aLugarTuristico()

        // 3. Verificación (Then)
        assertEquals("Colombia", lugarLocal.nombre)
        assertEquals("Bogotá", lugarLocal.capital)
        assertEquals("Americas", lugarLocal.region)
        assertEquals("url_bandera_png", lugarLocal.urlBandera)
        // Verificamos que la descripción contenga el nombre oficial
        assertEquals("Nombre oficial: República de Colombia", lugarLocal.descripcion)
    }
}