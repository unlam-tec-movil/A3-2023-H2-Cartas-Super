package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.database.guest.Guest
import ar.edu.unlam.mobile.scaffold.data.database.guest.GuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: GuestRepository
) : ViewModel(){

    private val _existeGuest = MutableLiveData<Boolean>()
    val existeGuest: LiveData<Boolean> = _existeGuest

    private fun crearUsuario(name: String): Guest {
        return Guest(null, name)
    }

    fun actualizarBase(name: String){
        val guest = crearUsuario(name)
        viewModelScope.launch {
            repository.addGuestInDatabase(guest)
        }
    }

    init {
        viewModelScope.launch {
            _existeGuest.value = repository.verifyDatabase()
        }

    }

}