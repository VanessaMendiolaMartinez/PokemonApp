package com.example.pokemonapp.domain.useCase

import com.example.pokemonapp.data.local.entities.toDatabase
import com.example.pokemonapp.data.network.models.PokemonDetailResponse
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.domain.model.Pokemon
import javax.inject.Inject

class PokemonListUseCase @Inject constructor(private val repository : PokemonRepository) {

    // Operador para obtener la lista de Pokémon
    suspend operator fun invoke(offset: Int, limit: Int): List<Pokemon> {
        val pokemon = repository.getAllPokemonList(offset, limit)
        return if(pokemon.isNotEmpty()){
            repository.clearPokemon()
            repository.insertPokemon(pokemon.map { it.toDatabase() })
            pokemon
        }else{
            repository.getAllPokemonDataBase()
        }
    }

    // Operador para obtener los detalles de un Pokémon
    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetailResponse {
        return repository.getPokemonDetails(pokemonId)
    }
}