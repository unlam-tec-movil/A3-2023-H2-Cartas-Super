package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.network.model.HeroApiModel
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.sensor.SensorDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionViewModelImp @Inject constructor(
    private val repo: IHeroRepository,
    private val sensorDataManager: SensorDataManager
) : ViewModel() {

    val sensorData = sensorDataManager.sensorData

    private val _heroList = MutableStateFlow<List<HeroApiModel>>(emptyList())
    val heroList = _heroList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _heroList.value = withContext(Dispatchers.IO) {
                repo.getAllHero()
            }
            _isLoading.value = false
        }
    }

    fun cancelSensorDataFlow() {
        sensorDataManager.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        sensorDataManager.cancel()
    }
}
