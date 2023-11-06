package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.domain.usuario.Guest
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

    private val _usuario = MutableLiveData<Guest>()
    val usuario: LiveData<Guest> = _usuario


    fun crearUsuario(name: String){
        val guest = Guest(1,name)
        viewModelScope.launch {
            repository.addGuestInDatabase(guest)
            obtenerUsuario()
        }
    }
    suspend fun obtenerUsuario(){
        val usuario = repository.usuarioExiste()

            if(usuario == null ){
                _existeGuest.value = false
            }else{
                _usuario.value = usuario!!
                _existeGuest.value = true
            }
    }

    init {
        viewModelScope.launch {
            obtenerUsuario()
        }

    }

}