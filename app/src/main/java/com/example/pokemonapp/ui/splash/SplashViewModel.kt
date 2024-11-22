package com.example.pokemonapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _navigateToOnboarding = MutableLiveData<Boolean>()
    val navigateToOnboarding: LiveData<Boolean>
        get() = _navigateToOnboarding

    // Función que inicia un temporizador para navegar después de un cierto tiempo
    fun startTimer() {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            _navigateToOnboarding.value = true
        }
    }

    companion object {
        // Duración del splash screen en milisegundos (3 segundos)
        private const val SPLASH_SCREEN_DURATION = 3000L
    }
}