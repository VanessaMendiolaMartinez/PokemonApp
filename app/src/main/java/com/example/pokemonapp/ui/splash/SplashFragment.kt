package com.example.pokemonapp.ui.splash
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokemonapp.R
import com.example.pokemonapp.data.preference.SessionManager
import com.example.pokemonapp.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    // Inyectar SessionManager con Hilt
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observa el LiveData que indica si debe navegar al siguiente destino
        viewModel.navigateToOnboarding.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                handleNavigation()
            }
        }

        // Inicia el temporizador para la navegación
        viewModel.startTimer()
    }

    private fun handleNavigation() {
        if (sessionManager.isLoggedIn()) {
            // Si el usuario tiene una sesión activa, navegar a la pantalla principal
            findNavController().navigate(R.id.action_splash_to_list_pokemon)
        } else {
            // Si no tiene sesión activa, navegar al onboarding/login
            findNavController().navigate(R.id.action_splash_to_signon)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
