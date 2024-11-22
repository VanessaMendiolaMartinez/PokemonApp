package com.example.pokemonapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentLoginBinding
import com.example.pokemonapp.utils.ValidationUtils.Companion.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // ViewModel que gestionará la lógica de la interfaz de usuario
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        observeViewModel()
    }

    // Método que configura la interacción con la interfaz de usuario (UI)
    private fun setUI() {
        binding.btnLogin.setOnClickListener {
            val email = binding.inputUserEmail.text.toString().trim()
            val password = binding.inputUserPassword.text.toString().trim()

            if (validateInput(email, password)) {
                viewModel.login(email, password)
            }
        }
    }

    // Método para validar el correo electrónico y la contraseña ingresados por el usuario
    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                binding.inputUserEmail.error = getString(R.string.error_email_empty)
                false
            }
            !isValidEmail(email) -> {
                // Si el email no es válido, muestra un mensaje de error
                binding.inputUserEmail.error = requireContext().getString(R.string.txt_signon_invalid_email)
                false
            }
            password.isBlank() -> {
                binding.inputUserPassword.error = getString(R.string.error_password_empty)
                false
            }
            else -> true
        }
    }

    // Método para observar el estado de autenticación del ViewModel

    private fun observeViewModel() {
        viewModel.authState.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                // Navegar a la lista de Pokémon después de un login exitoso
                findNavController().navigate(R.id.action_login_to_pokemon_list)
            } else if (result.isFailure) {
                // Mostrar un mensaje de error en caso de fallo
                showError(result.exceptionOrNull()?.message ?: getString(R.string.error_login_failed))
            }
        }
    }

    // Método para mostrar un mensaje de error en caso de fallo

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    // Método llamado cuando se destruye la vista del fragmento, limpia el binding para evitar fugas de memoria
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpiar la referencia del binding
    }
}
