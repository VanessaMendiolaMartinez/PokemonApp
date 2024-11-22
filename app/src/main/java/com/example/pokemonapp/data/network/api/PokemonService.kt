package com.example.pokemonapp.data.network.api

import com.example.pokemonapp.data.network.models.PokemonDetailResponse
import com.example.pokemonapp.data.network.models.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(private val api : PokemonApiClient) {

    suspend fun getPokemonList(offset: Int,  limit: Int): PokemonResponse {
        return withContext(Dispatchers.IO){
            val response = api.getPokemonList(offset, limit)
            return@withContext response
        }
    }

    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemonDetails(pokemonId)
            return@withContext response
        }
    }
}