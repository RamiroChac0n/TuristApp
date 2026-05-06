package com.example.segundodoparcialseminario2parteb.datos.remoto

import com.google.gson.annotations.SerializedName

// Modelo que representa la respuesta JSON de un país desde la API
data class PaisDto(
    @SerializedName("name")
    val nombre: NombrePais,

    @SerializedName("capital")
    val capital: List<String>?,   // Puede venir vacío en algunos países

    @SerializedName("region")
    val region: String,

    @SerializedName("subregion")
    val subregion: String?,

    @SerializedName("population")
    val poblacion: Long,

    @SerializedName("flags")
    val banderas: Banderas,

    @SerializedName("languages")
    val idiomas: Map<String, String>?,

    @SerializedName("area")
    val area: Double?
)

// Modelo para el campo "name" que contiene nombre común y oficial
data class NombrePais(
    @SerializedName("common")
    val comun: String,

    @SerializedName("official")
    val oficial: String
)

// Modelo para las URLs de las banderas
data class Banderas(
    @SerializedName("png")
    val png: String,

    @SerializedName("svg")
    val svg: String
)