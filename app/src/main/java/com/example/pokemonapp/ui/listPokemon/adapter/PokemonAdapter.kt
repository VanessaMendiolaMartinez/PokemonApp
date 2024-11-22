package com.example.pokemonapp.ui.listPokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.domain.model.Pokemon

class PokemonAdapter(
    private var pokemonList: List<Pokemon>,
    private val onClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namePokemon: TextView = view.findViewById(R.id.namePokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.namePokemon.text = pokemon.name
        holder.itemView.setOnClickListener { onClick(pokemon) }
    }

    override fun getItemCount(): Int = pokemonList.size

    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }
}
