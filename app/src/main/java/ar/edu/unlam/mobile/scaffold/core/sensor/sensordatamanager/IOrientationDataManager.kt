package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

// cambiar a CLASE ABSTRACTA /////////////////////////////////////////////////////////////////////
interface IOrientationDataManager : SensorEventListener {
    val sensorData: Flow<SensorData>

    fun cancel()

    override fun onSensorChanged(event: SensorEvent?)
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
}
