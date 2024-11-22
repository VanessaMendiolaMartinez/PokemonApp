package com.example.pokemonapp.domain.useCase

import android.content.Context
import com.example.pokemonapp.R
import com.example.pokemonapp.data.local.entities.UserEntity
import com.example.pokemonapp.data.preference.SessionManager
import com.example.pokemonapp.data.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager, // Inyecta SessionManager
    private val context: Context  // Se inyecta el contexto para acceder a los recursos
) {
    suspend operator fun invoke(email: String, password: String): Result<UserEntity?> {
        // Validaciones iniciales
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException(context.getString(R.string.error_email_empty)))
        }
        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException(context.getString(R.string.error_email_empty)))
        }

        // Llamada al repositorio para obtener el usuario por email
        val userResult = userRepository.login(email)

        // Verificar que el usuario existe y que la contraseña es válida
        return if (userResult.isSuccess) {
            val user = userResult.getOrNull()
            if (user != null && checkPassword(password, user.password)) {
                sessionManager.saveSession(user.email)
                Result.success(user) // Login exitoso
            } else {
                Result.failure(IllegalArgumentException(context.getString(R.string.error_invalid_email_or_password))) // Contraseña incorrecta o usuario no encontrado
            }
        } else {
            Result.failure(IllegalArgumentException(context.getString(R.string.error_invalid_email_or_password))) // Si el repositorio no encontró el usuario
        }
    }

    // Función para verificar la contraseña ingresada con el hash almacenado
    private fun checkPassword(enteredPassword: String, storedHash: String): Boolean {
        return BCrypt.checkpw(enteredPassword, storedHash) // Compara la contraseña ingresada con el hash almacenado
    }
}

