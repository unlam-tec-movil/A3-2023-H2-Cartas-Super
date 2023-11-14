package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.flow.StateFlow

interface ICollectionViewModel {
    fun cancelSensorDataFlow()

    val heroList: StateFlow<List<HeroModel>>
    val isLoading: StateFlow<Boolean>
    val sensorData: StateFlow<SensorData>
}
