package com.example.segundodoparcialseminario2parteb.datos.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad de un lugar turístico guardado en la base de datos local
@Entity(tableName = "lugares_turisticos")
data class LugarTuristico(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val capital: String,
    val region: String,
    val descripcion: String,
    val urlBandera: String,
    val esFavorito: Boolean = true
)