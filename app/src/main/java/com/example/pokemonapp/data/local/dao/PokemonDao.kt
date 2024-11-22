package com.example.pokemonapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonapp.data.local.entities.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon:List<PokemonEntity>)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAllPokemon()
}