package com.example.pokemonapp.domain.useCase

import android.content.Context
import com.example.pokemonapp.R
import com.example.pokemonapp.data.local.entities.UserEntity
import com.example.pokemonapp.data.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val context: Context // Se inyecta el contexto para acceder a los recursos
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        // Validaciones iniciales
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException(context.getString(R.string.error_email_empty)))
        }
        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException(context.getString(R.string.error_password_empty)))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException(context.getString(R.string.error_password_short)))
        }

        // Encriptar la contraseña antes de crear el usuario
        val encryptedPassword = encryptPassword(password)

        // Crear el usuario con la contraseña encriptada
        val user = UserEntity(email = email, password = encryptedPassword)

        // Llamada al repositorio para registrar al usuario
        return userRepository.register(user)
    }

    // Función para encriptar la contraseña
    private fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt()) // Encripta la contraseña usando BCrypt
    }
}

