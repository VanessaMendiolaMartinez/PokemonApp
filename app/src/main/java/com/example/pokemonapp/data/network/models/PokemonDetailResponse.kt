package com.example.pokemonapp.data.network.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Double,
    @SerializedName("weight") val weight: Double,
    @SerializedName("abilities") val abilities: List<Ability>,
    @SerializedName("sprites") val sprites: Sprites
)

data class Ability(
    @SerializedName("ability") val ability: AbilityInfo
)

data class AbilityInfo(
    @SerializedName("name") val name: String
)

data class Sprites(
    @SerializedName("front_default") val front_default: String
)

