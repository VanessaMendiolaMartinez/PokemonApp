package com.example.pokemonapp.data.network.helper

import android.content.Context
import android.net.ConnectivityManager

import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class NetworkHelper @Inject constructor(
    private val context: Context
) {

    // Método para verificar si hay acceso a la red
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Desde Android 10, usamos NetworkCapabilities para verificar las redes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            networkCapabilities?.let {
                // Verificamos si hay alguna red con acceso a Internet
                return it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        } else {
            // En versiones anteriores a Android M, simplemente verificamos si hay una red activa
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        return false
    }
}
