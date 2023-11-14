package ar.edu.unlam.mobile.scaffold.core.sensor

import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import kotlinx.coroutines.flow.StateFlow

interface IUseSensorData {
    val sensorData: StateFlow<SensorData>
    fun cancelSensorDataFlow()
}
