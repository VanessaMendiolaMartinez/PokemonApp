package com.example.pokemonapp.ui

import androidx.navigation.NavController

interface NavGraphChanger {
    fun setNavGraph(graphResId: Int)
    fun getNavController(): NavController
}