package com.example.pokemonapp.ui.signon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokemonapp.R
import com.example.pokemonapp.data.preference.SessionManager
import com.example.pokemonapp.databinding.FragmentSignOnBinding
import com.example.pokemonapp.utils.ValidationUtils.Companion.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignOnFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var _binding : FragmentSignOnBinding? = null
    private val binding get() = _binding!!

    private val viewModel : SignOnViewModel  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignOnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        observeViewModel()
    }

    // Configura los elementos de la interfaz de usuario como los listeners de los botones
    private fun setUI(){
        binding.btnSignOn.setOnClickListener{
            // Obtener el email y la contraseña ingresados por el usuario
            val email = binding.inputUserEmail.text.toString().trim()
            val password = binding.inputUserPassword.text.toString().trim()

            // Validar la entrada y proceder con el registro si es válido
            if (validateInput(email, password)) {
                viewModel.register(email, password)
            }
        }

        // Listener del botón de "Login" - Navega al fragmento de login
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_sign_on_to_login)
        }
    }

    // Valida la entrada de email y contraseña
    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                // Si el email está vacío, muestra un mensaje de error
                binding.inputUserEmail.error = requireContext().getString(R.string.txt_signon_email_empty)
                false
            }
            !isValidEmail(email) -> {
                // Si el email no es válido, muestra un mensaje de error
                binding.inputUserEmail.error = requireContext().getString(R.string.txt_signon_invalid_email)
                false
            }
            password.isBlank() -> {
                // Si la contraseña está vacía, muestra un mensaje de error
                binding.inputUserPassword.error =  requireContext().getString(R.string.txt_signon_password_empty)
                false
            }
            password.length < 6 -> {
                // Si la contraseña tiene menos de 6 caracteres, muestra un mensaje de error
                binding.inputUserPassword.error = requireContext().getString(R.string.txt_signon_password_characters)

                false
            }
            else -> true
        }
    }

    private fun observeViewModel() {
        viewModel.registerState.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                // Guardar sesión
                val email = binding.inputUserEmail.text.toString().trim()
                sessionManager.saveSession(email)
                // Navegar a la pantalla principal
                findNavController().navigate(R.id.action_sign_on_to_pokemon_list)
            } else if (result.isFailure) {
                // Mostrar mensaje de error
                showError(result.exceptionOrNull()?.message ?: getString(R.string.txt_signon_error))
            }
        }
    }

    // Muestra un mensaje de error
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}