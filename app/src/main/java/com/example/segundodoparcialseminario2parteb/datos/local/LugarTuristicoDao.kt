package com.example.segundodoparcialseminario2parteb.datos.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// DAO con las operaciones básicas sobre la tabla de lugares turísticos
@Dao
interface LugarTuristicoDao {

    // Si ya existe con el mismo id, lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(lugar: LugarTuristico)

    @Query("SELECT * FROM lugares_turisticos ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<LugarTuristico>>

    @Query("SELECT * FROM lugares_turisticos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): LugarTuristico?

    @Delete
    suspend fun eliminar(lugar: LugarTuristico)

    @Update
    suspend fun actualizar(lugar: LugarTuristico)
}