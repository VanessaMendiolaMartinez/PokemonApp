package com.example.pokemonapp.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment(){
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ViewModel que maneja la lógica relacionada con el perfil
    private lateinit var profileViewModel: ProfileViewModel

    // Códigos de solicitud para permisos
    private val CAMERA_PERMISSION_REQUEST_CODE = 101
    private val STORAGE_PERMISSION_REQUEST_CODE = 102

    // Códigos para solicitudes de imágenes (cámara o galería)
    private val PICK_IMAGE_REQUEST = 1
    private val CAMERA_REQUEST = 2

    // Bandera para saber si los permisos ya fueron solicitados
    private var permissionsRequested = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        // Obtiene el ViewModel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Verificamos si los permisos ya han sido solicitados previamente
        if (!permissionsRequested) {
            checkPermissions()
        }

        // Observamos el estado del avatar
        profileViewModel.avatarUri.observe(viewLifecycleOwner, Observer { uri ->
            // Actualiza el ImageView con la nueva imagen
            uri?.let {
                binding.avatarImageView.setImageURI(uri)
            }
        })

        // Establecemos el clic en el botón para cambiar el avatar
        binding.selectAvatarButton.setOnClickListener {
            showImageSourceDialog()
        }

        // Configuración de la barra de herramientas
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbarContainer.toolbar)

        setUI()
    }

    // Establece la UI (barra de herramientas y otros elementos)
    private fun setUI(){
        binding.toolbarContainer.toolbarTitle.text = requireContext().getString(R.string.txt_profile_title)
        binding.toolbarContainer.btnExit.setOnClickListener{
            findNavController().popBackStack()  // Para retroceder a la pantalla anterior
        }
        binding.toolbarContainer.profileIcon.visibility = View.GONE
    }

    // Verifica si el permiso de cámara está concedido
    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    // Verifica si el permiso de almacenamiento está concedido
    private fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    // Solicita los permisos de cámara y almacenamiento si no están concedidos
    private fun checkPermissions() {
        if (!isCameraPermissionGranted() || !isStoragePermissionGranted()) {
            // Solicitar permisos solo si no están concedidos
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            permissionsRequested = true  // Marcar que los permisos ya han sido concedidos
        }
    }

    // Muestra un diálogo con opciones para seleccionar la fuente de la imagen (cámara o galería)
    private fun showImageSourceDialog() {
        val options = arrayOf(requireContext().getString(R.string.txt_profile_button_take_photo), requireContext().getString(R.string.txt_profile_button_selecet_photo))
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(requireContext().getString(R.string.txt_profile_button_selecet_source))
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera() // Abrir la cámara si se elige la primera opción
                1 -> openGallery() // Abrir la galería si se elige la segunda opción
            }
        }
        builder.show()
    }

    // Inicia la cámara para tomar una foto
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, CAMERA_REQUEST)
        }
    }

    // Inicia la galería para seleccionar una foto
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Maneja el resultado de las actividades (cámara o galería)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    data?.data?.let { uri ->
                        // Actualiza el avatar en el ViewModel
                        profileViewModel.updateAvatar(uri)
                    }
                }
                CAMERA_REQUEST -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    // Convertir el Bitmap a Uri, si es necesario, o manejarlo como lo necesites
                    val tempUri = getImageUriFromBitmap(photo)
                    profileViewModel.updateAvatar(tempUri)
                }
            }
        }
    }

    // Convierte un Bitmap en un URI
    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val file = File(requireContext().cacheDir, "avatar_image.jpg")
        val outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        return Uri.fromFile(file)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}