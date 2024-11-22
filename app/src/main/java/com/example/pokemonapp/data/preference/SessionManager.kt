package com.example.pokemonapp.data.preference

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(
    @ApplicationContext context: Context // Context inyectado por Hilt
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_EMAIL = "key_email"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    // Guarda el estado de la sesión
    fun saveSession(email: String) {
        sharedPreferences.edit().apply {
            putString(KEY_EMAIL, email)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    // Recupera el estado de la sesión
    fun getSession(): String? {
        return if (isLoggedIn()) {
            sharedPreferences.getString(KEY_EMAIL, null)
        } else {
            null
        }
    }

    // Verifica si el usuario está logueado
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Cierra la sesión
    fun clearSession() {
        sharedPreferences.edit().apply {
            remove(KEY_EMAIL)
            remove(KEY_IS_LOGGED_IN)
            apply()
        }
    }
}