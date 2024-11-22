package com.example.pokemonapp.data.network.api

import com.example.pokemonapp.data.network.models.PokemonDetailResponse
import com.example.pokemonapp.data.network.models.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiClient {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: Int
    ): PokemonDetailResponse
}