package com.example.pokemonapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemonapp.domain.model.Pokemon

@Entity(tableName = "pokemon_table")
data class PokemonEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,  // Cambiado a Long
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo (name = "url") val url : String)

fun Pokemon.toDatabase() = PokemonEntity(name = name, url = url)