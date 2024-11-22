package com.example.pokemonapp.ui.detailsPokemon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.network.models.PokemonDetailResponse
import com.example.pokemonapp.data.network.helper.NetworkHelper
import com.example.pokemonapp.domain.useCase.PokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonListUseCase: PokemonListUseCase,
    private val networkHelper: NetworkHelper // Inyectamos el NetworkHelper en el ViewModel
) : ViewModel() {

    val pokemonDetail = MutableLiveData<PokemonDetailResponse>()
    val isConnected = MutableLiveData<Boolean>() // LiveData para la conexión
    val errorMessage = MutableLiveData<String>() // Para manejar errores

    init {
        // Verificamos el estado de la conexión al inicializar
        checkNetworkStatus()
    }

    // Función que verifica la conexión
    private fun checkNetworkStatus() {
        isConnected.postValue(networkHelper.isNetworkAvailable())
    }

    // Función para obtener los detalles del Pokémon
    fun getPokemonDetails(pokemonId: Int) {
        if (networkHelper.isNetworkAvailable()) {
            // Si hay conexión, hacemos la llamada a la API
            viewModelScope.launch {
                try {
                    val result = pokemonListUseCase.getPokemonDetails(pokemonId)
                    pokemonDetail.postValue(result)
                } catch (e: Exception) {
                    // Si la llamada a la API falla por otro motivo
                    errorMessage.postValue("Error al obtener el Pokemón: ${e.message}")
                }
            }
        }
    }

    // También puedes reintentar la solicitud si la conexión cambia (por ejemplo, cuando se recupere la conexión)
    fun retryRequestIfConnected(pokemonId: Int) {
        if (networkHelper.isNetworkAvailable()) {
            getPokemonDetails(pokemonId) // Reintenta la solicitud
        }
    }
}

