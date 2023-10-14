package ar.edu.unlam.mobile.scaffold.domain.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/*
class OrientationDataManager(context: Context) : SensorEventListener {

    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
 */
class OrientationDataManager @Inject constructor(private val sensorManager: SensorManager) : SensorEventListener {

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null
    private var rotationVector: FloatArray? = null

    private val data: Channel<SensorData> = Channel(Channel.UNLIMITED)

    val sensorData = flow {
        init()
        data.receiveAsFlow().collect {
            emit(it)
        }
    }

    /*
        TYPE_ACCELEROMETER
        Type: Hardware
        Computes the acceleration in m/s2 applied on all three axes (x, y and z),
        including the force of gravity.

        TYPE_GRAVITY
        Type: Software or Hardware
        Computes the gravitational force in m/s2 applied on all three axes (x, y and z).

        TYPE_MAGNETIC_FIELD
        Type: Hardware
        Computes the geomagnetic field for all three axes in tesla (μT).

        Existe el Sensor.TYPE_ACCELEROMETER, creo que no lo usa porque TYPE_GRAVITY puede ser
            emulado por software usando otros sensores.
     */
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

        /*
        var accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        if (accelerometer == null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }

        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val isAccelerometerNotSuccessful = !sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
        val isMagnetometerNotSuccessful = !sensorManager.registerListener(
            this,
            magnetometer,
            SensorManager.SENSOR_DELAY_UI
        )
        if (isAccelerometerNotSuccessful || isMagnetometerNotSuccessful) {
            Log.d(
                "OrientationDataManager",
                "accelerometer: ${!isAccelerometerNotSuccessful}" +
                    " magnetometer: ${!isMagnetometerNotSuccessful}"
            )
            cancel()
            /*
            throw OrientationDataManagerException(
                message = "El sensor acelerómetro no está disponible en este dispositivo."
            )*/
        }

         */
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
        /*
        if (event?.sensor?.type == Sensor.TYPE_GRAVITY || event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values
        }

        if (gravity != null && geomagnetic != null) {
            var rotationMatrix = FloatArray(9)
            var i = FloatArray(9)

            if (SensorManager.getRotationMatrix(rotationMatrix, i, gravity, geomagnetic)) {
                var orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)

                data.trySend(
                    SensorData(
                        roll = orientation[2], // Roll (rotation around the y-axis)
                        pitch = orientation[1] // Pitch (rotation around the x-axis)
                    )
                )
            }
        }

         */
    }

    fun cancel() {
        Log.d("OrientationDataManager", "cancel")
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

data class SensorData(
    val roll: Float, // Roll (rotation around the y-axis)
    val pitch: Float // Pitch (rotation around the x-axis)
)
