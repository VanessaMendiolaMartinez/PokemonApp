package com.example.pokemonapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityMainContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainContainerActivity : AppCompatActivity(), NavGraphChanger{

    private lateinit var binding: ActivityMainContainerBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el fragmento de navegación
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_sing_on) as NavHostFragment
        navController = navHostFragment.navController

        // Establece el gráfico de navegación inicial
        setNavGraph(R.navigation.nav_graph_pokemon)
    }

    // Maneja el botón de navegación hacia atrás
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // Cambia el gráfico de navegación
    override fun setNavGraph(graphResId: Int) {
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(graphResId)
        navController.graph = graph
    }

    // Obtiene el controlador de navegación
    override fun getNavController(): NavController {
        return navController
    }
}