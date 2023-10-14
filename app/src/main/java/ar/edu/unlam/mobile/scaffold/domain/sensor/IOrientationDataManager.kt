package ar.edu.unlam.mobile.scaffold.domain.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

interface IOrientationDataManager : SensorEventListener {
    val sensorData: Flow<SensorData>

    fun cancel()

    override fun onSensorChanged(event: SensorEvent?)
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
}
