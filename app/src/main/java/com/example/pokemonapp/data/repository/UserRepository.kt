package com.example.pokemonapp.data.repository

import android.util.Log
import com.example.pokemonapp.data.local.dao.UserDao
import com.example.pokemonapp.data.local.entities.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    // Este método solo recupera al usuario por email, la verificación de la contraseña se hace en el caso de uso
    suspend fun login(email: String): Result<UserEntity?> {
        return try {
            val user = userDao.loginUserByEmail(email)
            if (user != null) {
                Result.success(user) // El usuario existe, lo devolvemos
            } else {
                Result.failure(IllegalArgumentException("El usuario no fue encontrado")) // El usuario no fue encontrado
            }
        } catch (e: Exception) {
            Result.failure(e) // Si ocurre algún error en la base de datos
        }
    }


    suspend fun register(user: UserEntity): Result<Unit> {
        return try {
            userDao.registerUser(user)
            val users = userDao.getAllUsers() // Recuperar todos los usuarios
            Log.d("UserRepository", "Usuarios registrados: $users") // Imprimir la lista de usuarios en Logcat
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
