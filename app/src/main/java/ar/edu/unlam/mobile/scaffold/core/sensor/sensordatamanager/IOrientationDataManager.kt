package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

interface IOrientationDataManager : SensorEventListener {

    fun cancel()

    override fun onSensorChanged(event: SensorEvent?)
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    fun getSensorData(): Flow<SensorData>
}
