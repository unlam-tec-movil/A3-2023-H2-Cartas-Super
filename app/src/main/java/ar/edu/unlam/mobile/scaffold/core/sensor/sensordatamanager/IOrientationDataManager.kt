package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

/*
https://proandroiddev.com/parallax-effect-with-sensormanager-using-jetpack-compose-a735a2f5811b
https://stackoverflow.com/questions/4819626/android-phone-orientation-overview-including-compass/6804786#6804786
https://gist.github.com/surajsau/b178e2646a1240f883774811e15bbb6a
 */

interface IOrientationDataManager : SensorEventListener {

    fun cancel()

    override fun onSensorChanged(event: SensorEvent?)
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    fun getSensorData(): Flow<SensorData>
}

data class SensorData(
    val roll: Float = 0f, // Roll (rotation around the y-axis)
    val pitch: Float = 0f // Pitch (rotation around the x-axis)
)
