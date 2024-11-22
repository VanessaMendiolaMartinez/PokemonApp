package com.example.pokemonapp.ui.listPokemon

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.example.pokemonapp.R
import com.example.pokemonapp.data.network.helper.NetworkHelper
import com.example.pokemonapp.domain.useCase.PokemonListUseCase
import com.example.pokemonapp.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonUseCase: PokemonListUseCase,
    private val networkHelper: NetworkHelper // Inyectamos el NetworkHelper en el ViewModel
): ViewModel() {

    val pokemonModel = MutableLiveData<List<Pokemon>>()
    val isConnected = MutableLiveData<Boolean>() // LiveData para la conexión
    val errorMessage = MutableLiveData<String>() // Para manejar errores

    init {
        checkNetworkStatus() // Verificamos la red al iniciar
    }

    private fun checkNetworkStatus() {
        isConnected.postValue(networkHelper.isNetworkAvailable()) // Publicamos el estado de la conexión
    }

    fun onCreate(context: Context) {
        viewModelScope.launch {
            try {
                // Verificamos si hay conexión antes de hacer la llamada
                if (networkHelper.isNetworkAvailable()) {
                    val result = getPokemonUseCase(300, 100)
                    if (result.isNotEmpty()) {
                        pokemonModel.postValue(result)
                    } else {
                        errorMessage.postValue(
                            context.getString(R.string.no_pokemon_found)
                        )
                    }
                } else {
                    errorMessage.postValue(
                        context.getString(R.string.check_internet_connection)
                    )
                }
            } catch (e: Exception) {
                // Manejo de cualquier error de la llamada a la API
                errorMessage.postValue(
                    String.format(
                        context.getString(R.string.error_fetching_pokemon),
                        e.message
                    )
                )
            }
        }
    }
}

