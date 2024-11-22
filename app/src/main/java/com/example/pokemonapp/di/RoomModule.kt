package com.example.pokemonapp.di

import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.data.local.roomDatabase.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val POKEMON_DATABASE_NAME = "pokemon_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, RoomDatabase::class.java, POKEMON_DATABASE_NAME).build()


    @Singleton
    @Provides
    fun providePokemonDao(db: RoomDatabase) = db.getPokemonDao()

    @Singleton
    @Provides
    fun provideUserDao(db: RoomDatabase) = db.userDao()
}