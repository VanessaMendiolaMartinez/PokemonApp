package com.example.pokemonapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.local.entities.UserEntity
import com.example.pokemonapp.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<Result<UserEntity?>>()
    val authState: LiveData<Result<UserEntity?>> get() = _authState

    // Función para realizar el login con el email y la contraseña proporcionados
    fun login(email: String, password: String) {
        // Se ejecuta en el scope del ViewModel para que la operación sea gestionada correctamente por la arquitectura de coroutines
        viewModelScope.launch {
            // Se llama al caso de uso para realizar la autenticación y se actualiza el estado de la autenticación
            _authState.value = loginUserUseCase(email, password)
        }
    }
}
