package com.example.pokemonapp.utils

import android.util.Patterns

class ValidationUtils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}
