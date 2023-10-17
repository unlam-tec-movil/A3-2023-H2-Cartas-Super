package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
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
    private val orientationDataManager: IOrientationDataManager
) : ViewModel() {

    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData = _sensorData.asStateFlow()

    private val _hero = MutableStateFlow(DataHero())
    val hero = _hero.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun getHero(id: Int) {
        viewModelScope.launch {
            orientationDataManager.getSensorData().collect {
                _sensorData.value = it
            }
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
