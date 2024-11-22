package com.example.pokemonapp.data.repository

import com.example.pokemonapp.data.local.dao.PokemonDao
import com.example.pokemonapp.data.local.entities.PokemonEntity
import com.example.pokemonapp.data.network.models.PokemonDetailResponse
import com.example.pokemonapp.data.network.models.PokemonResponse
import com.example.pokemonapp.data.network.api.PokemonService
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.toDomain
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api : PokemonService,
                                            private val pokemonDao : PokemonDao
) {

    // Método para obtener la lista de Pokémon
    suspend fun getAllPokemonList(offset: Int, limit: Int): List<Pokemon> {
        val response: PokemonResponse = api.getPokemonList(offset, limit)
        return response.results.map { it.toDomain() }
    }

    // Método para obtener los detalles de un Pokémon
    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetailResponse {
        return api.getPokemonDetails(pokemonId)
    }

    suspend fun getAllPokemonDataBase():List<Pokemon>{
        val response : List<PokemonEntity> = pokemonDao.getAllPokemons()
        return response.map { it.toDomain() }
    }

    suspend fun insertPokemon(pokemon: List<PokemonEntity>){
        pokemonDao.insertAll(pokemon)
    }

    suspend fun clearPokemon(){
        pokemonDao.deleteAllPokemon()
    }
}
