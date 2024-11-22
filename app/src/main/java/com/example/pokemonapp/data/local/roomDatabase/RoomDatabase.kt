package com.example.pokemonapp.data.local.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemonapp.data.local.dao.PokemonDao
import com.example.pokemonapp.data.local.dao.UserDao
import com.example.pokemonapp.data.local.entities.PokemonEntity
import com.example.pokemonapp.data.local.entities.UserEntity

@Database(entities = [PokemonEntity::class, UserEntity::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
    abstract fun userDao() : UserDao
}
