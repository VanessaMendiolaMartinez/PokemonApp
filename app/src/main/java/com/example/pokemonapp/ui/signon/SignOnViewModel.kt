package com.example.pokemonapp.ui.signon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.domain.useCase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignOnViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerState = MutableLiveData<Result<Unit>>()
    val registerState: LiveData<Result<Unit>> = _registerState

    // Función para registrar al usuario, llamada desde el fragmento
    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = registerUserUseCase(email, password)
        }
    }
}
