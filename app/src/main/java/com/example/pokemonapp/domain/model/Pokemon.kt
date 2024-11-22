package com.example.pokemonapp.domain.model

import com.example.pokemonapp.data.local.entities.PokemonEntity
import com.example.pokemonapp.data.network.models.PokemonModel


data class Pokemon(val name : String, val url : String)

fun PokemonModel.toDomain() = Pokemon(name, url)
fun PokemonEntity.toDomain() = Pokemon(name, url)