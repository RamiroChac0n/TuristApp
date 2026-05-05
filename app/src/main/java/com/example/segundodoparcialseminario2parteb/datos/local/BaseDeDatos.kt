package com.example.segundodoparcialseminario2parteb.datos.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Base de datos Room con la entidad LugarTuristico
@Database(entities = [LugarTuristico::class], version = 1, exportSchema = false)
abstract class BaseDeDatos : RoomDatabase() {

    // Expone el DAO para acceder a las operaciones
    abstract fun lugarDao(): LugarTuristicoDao

    companion object {
        // Instancia única (singleton) para evitar múltiples conexiones (Curso de Anàlisis 2 :D)
        @Volatile
        private var INSTANCIA: BaseDeDatos? = null

        fun obtenerBaseDeDatos(context: Context): BaseDeDatos {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "base_datos_turismo"
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}