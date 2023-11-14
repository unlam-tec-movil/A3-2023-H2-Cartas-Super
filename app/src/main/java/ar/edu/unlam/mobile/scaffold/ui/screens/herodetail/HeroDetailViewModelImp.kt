package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
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
) : ViewModel(), IHeroDetailViewModel {

    private val _sensorData = MutableStateFlow(SensorData())
    override val sensorData = _sensorData.asStateFlow()

    private val _hero = MutableStateFlow(HeroModel())
    override val hero = _hero.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    override val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            orientationDataManager.getSensorData().collect {
                _sensorData.value = it
            }
        }
    }

    override fun getHero(heroID: Int) {
        viewModelScope.launch {
            _hero.value = withContext(Dispatchers.IO) {
                repo.getHero(heroID)
            }
            _isLoading.value = false
        }
    }

    override fun cancelSensorDataFlow() {
        orientationDataManager.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        orientationDataManager.cancel()
    }
}
