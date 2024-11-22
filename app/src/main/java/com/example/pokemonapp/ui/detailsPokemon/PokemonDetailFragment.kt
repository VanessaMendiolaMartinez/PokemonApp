package com.example.pokemonapp.ui.detailsPokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentDetailsPokemonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentDetailsPokemonBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokemonDetailViewModel by viewModels()
    private val args: PokemonDetailFragmentArgs by navArgs()

    private lateinit var progressBar: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsPokemonBinding.inflate(inflater, container, false)
        progressBar = binding.root.findViewById(R.id.progressBarLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbarContainer.toolbar)

        setUI()

        val pokemonId = args.pokemonId

        // Llamada al ViewModel para obtener los detalles del Pokémon
        viewModel.getPokemonDetails(pokemonId)
        showLoading(true)

        // Observar el LiveData para obtener la respuesta
        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer { pokemon ->
            pokemon?.let {
                showLoading(false)
                // Actualizar la UI con los detalles del Pokémon
                binding.pokemonName.text = it.name
                binding.pokemonId.text = getString(R.string.txt_pokemon_id, it.id)
                binding.pokemonHeight.text = getString(R.string.txt_pokemon_height, it.height)
                binding.pokemonWeight.text = getString(R.string.txt_pokemon_weight, it.weight)
                binding.pokemonAbilities.text = getString(R.string.txt_pokemon_abilities,
                    it.abilities.joinToString { ability -> ability.ability.name })

                // Cargar la imagen con Glide
                Glide.with(requireContext())
                    .load(it.sprites.front_default)
                    .into(binding.pokemonImage)
            }
        })

        viewModel.isConnected.observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected) {
                // Si hay conexión, intenta cargar los detalles
                viewModel.retryRequestIfConnected(pokemonId)
            } else {
                // Si no hay conexión, muestra un mensaje de desconexión
                Toast.makeText(requireContext(), getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show()
            }
        })

        // Observar los errores
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showErrorToast(errorMessage) // Muestra el mensaje de error
            }
        })
    }

    // Método para configurar la UI inicial, incluyendo el título del toolbar y el botón de retroceso
    private fun setUI() {
        binding.toolbarContainer.toolbarTitle.text = requireContext().getString(R.string.txt_detail_title)
        binding.toolbarContainer.btnExit.setOnClickListener{
            findNavController().popBackStack()  // Para retroceder a la pantalla anterior
        }
        binding.toolbarContainer.profileIcon.visibility = View.GONE
    }

    // Método para mostrar u ocultar el indicador de carga (progress bar)
    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Método para mostrar un mensaje de error en forma de Toast
    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // Método que se ejecuta cuando el fragmento es destruido, limpia el binding
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}