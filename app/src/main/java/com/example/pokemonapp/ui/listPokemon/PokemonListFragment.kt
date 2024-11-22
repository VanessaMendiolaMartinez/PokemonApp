package com.example.pokemonapp.ui.listPokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.data.preference.SessionManager
import com.example.pokemonapp.databinding.FragmentListPokemonBinding
import com.example.pokemonapp.ui.listPokemon.adapter.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var _binding : FragmentListPokemonBinding? = null
    private val viewModel: PokemonListViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var progressBar: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListPokemonBinding.inflate(inflater, container, false)
        progressBar = binding.root.findViewById(R.id.progressBarLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbarContainer.toolbar)
        viewModel.onCreate(requireContext())
        setUI()
        showLoading(true)

        // Observa el LiveData del ViewModel para obtener la lista de Pokémon
        viewModel.pokemonModel.observe(viewLifecycleOwner, Observer { pokemonList ->
            showLoading(false)
            // Configura el RecyclerView con la lista de Pokémon
            binding.rvPokemon.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPokemon.adapter = PokemonAdapter(pokemonList){pokemonList->
                // Cuando se selecciona un Pokémon, se navega a los detalles de ese Pokémon
                val pokemonId = pokemonList.url.split("/").getOrNull(6)?.toIntOrNull()
                if (pokemonId != null) {
                    val action = PokemonListFragmentDirections.actionPokemonListToPokemonDetail(pokemonId)
                    findNavController().navigate(action)
                }
            }
        })

        // Observa el mensaje de error en el ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showErrorToast(errorMessage) // Muestra el mensaje de error
            }
        })
    }

    // Método para configurar la UI del fragmento (como los iconos de la barra de herramientas)
    private fun setUI(){
        binding.toolbarContainer.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_pokemon_list_to_profile)
        }
        binding.toolbarContainer.toolbarTitle.text = requireContext().getString(R.string.txt_pokemon_list)
        binding.toolbarContainer.btnExit.visibility = View.GONE
    }

    // Método para controlar la visibilidad del ProgressBar (cargando o no cargando)
    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Método para mostrar un mensaje de error en caso de que ocurra algún fallo
    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // Inflar el menú de opciones para este fragmento
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu) // Inflar el menú en el fragmento
    }

    // Manejo de las opciones seleccionadas del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                sessionManager.clearSession()
                // Navegar al flujo de login
                findNavController().navigate(R.id.action_profile_to_sign_on)
                logoutUser()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Método que maneja el cierre de sesión y muestra un mensaje de confirmación
    private fun logoutUser() {
        Toast.makeText(requireContext(), requireContext().getString(R.string.txt_pokemon_sesión_cerrada), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}