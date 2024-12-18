package com.example.pokemonapp.data.network.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<PokemonModel>
)

data class PokemonModel(@SerializedName("name") val name : String, @SerializedName("url") val url : String)