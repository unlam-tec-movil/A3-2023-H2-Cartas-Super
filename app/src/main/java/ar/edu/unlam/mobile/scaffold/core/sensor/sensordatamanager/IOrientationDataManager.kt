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

    /*
    https://stackoverflow.com/questions/32372847/android-algorithms-for-sensormanager-getrotationmatrix-and-sensormanager-getori
     hay que limitar el rotationVector para que los cálculos tengan sensito geométricamente
     */
    fun limitRotationVectorRange(rotationVector: FloatArray, limit: Float = 3.1f) {
        when (rotationVector[0]) {
            in Float.NEGATIVE_INFINITY..(-limit) -> rotationVector[0] = -limit
            in limit..Float.POSITIVE_INFINITY -> rotationVector[0] = limit
            else -> {}
        }
        when (rotationVector[1]) {
            in Float.NEGATIVE_INFINITY..(-limit) -> rotationVector[0] = -limit
            in limit..Float.POSITIVE_INFINITY -> rotationVector[0] = limit
            else -> {}
        }
        when (rotationVector[2]) {
            in Float.NEGATIVE_INFINITY..(-limit) -> rotationVector[0] = -limit
            in limit..Float.POSITIVE_INFINITY -> rotationVector[0] = limit
            else -> {}
        }
    }
}
