package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.sensor.sensordatamanager.OrientationDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModelImp @Inject constructor(
    private val repo: IHeroRepository,
    private val orientationDataManager: OrientationDataManager
) : ViewModel() {

    val sensorData = orientationDataManager.sensorData

    private val _hero = MutableStateFlow(DataHero())
    val hero = _hero.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun getHero(id: Int) {
        viewModelScope.launch {
            _hero.value = withContext(Dispatchers.IO) {
                repo.getHero(id)
            }
            _isLoading.value = false
        }
    }

    fun cancelSensorDataFlow() {
        orientationDataManager.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        orientationDataManager.cancel()
    }
}
