package com.example.pokemonapp.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    private val _avatarUri = MutableLiveData<Uri?>()
    val avatarUri: LiveData<Uri?> get() = _avatarUri

    // Función para actualizar el avatar
    fun updateAvatar(uri: Uri) {
        _avatarUri.value = uri
    }
}
