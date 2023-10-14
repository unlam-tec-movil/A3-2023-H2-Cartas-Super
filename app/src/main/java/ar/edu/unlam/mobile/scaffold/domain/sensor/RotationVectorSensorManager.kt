package ar.edu.unlam.mobile.scaffold.domain.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class RotationVectorSensorManager @Inject constructor(
    private val sensorManager: SensorManager,
) : IOrientationDataManager {
    override val sensorData = flow {
        init()
        data.receiveAsFlow().collect {
            emit(it)
        }
    }

    private val data: Channel<SensorData> = Channel(Channel.UNLIMITED)
    private var rotationVector: FloatArray? = null

    private fun init() {
        Log.d("OrientationDataManager", "init")
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        var sensor = sensorList.find { it.type == Sensor.TYPE_ROTATION_VECTOR }
        if (sensor != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        } else {
            return
        }
        val isRegisterListenerNotSuccessful = !sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_UI
        )
        if (isRegisterListenerNotSuccessful) {
            Log.d(
                "OrientationDataManager",
                "el registro de escucha del sensor de tipo vector de rotación no fue exitoso"
            )
            cancel()
        }
    }

    override fun cancel() {
        Log.d("OrientationDataManager", "cancel")
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            rotationVector = event.values
        }
        if (rotationVector != null) {
            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)
            val remappedRotationMatrix = FloatArray(16)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                remappedRotationMatrix
            )
            val orientation = FloatArray(3)
            SensorManager.getOrientation(remappedRotationMatrix, orientation)
            data.trySend(
                SensorData(
                    roll = orientation[2], // Roll (rotation around the y-axis)
                    pitch = orientation[1] // Pitch (rotation around the x-axis)
                )
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
