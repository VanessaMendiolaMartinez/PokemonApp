package com.example.pokemonapp.di

import android.app.Application
import android.content.Context
import com.example.pokemonapp.data.network.helper.NetworkHelper
import com.example.pokemonapp.data.network.api.PokemonApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Activity
object NetworkModule {

    @Singleton
    @Provides
    //proveo Retrofit
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()) // Para convertir JSON a objetos
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonApiClient(retrofit: Retrofit): PokemonApiClient {
        return retrofit.create(PokemonApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}