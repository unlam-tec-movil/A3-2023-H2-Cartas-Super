package ar.edu.unlam.mobile.scaffold.di

import android.content.Context
import android.hardware.SensorManager
import ar.edu.unlam.mobile.scaffold.domain.sensor.SensorFactory
import ar.edu.unlam.mobile.scaffold.domain.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.domain.sensor.sensordatamanager.OrientationDataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // muere cuando el viewmodel es destruido
object SensorModule {

    @ViewModelScoped
    @Provides
    fun providesSensorManager(@ApplicationContext context: Context): SensorManager {
        val sensorManager by lazy {
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
        return sensorManager
    }

    @ViewModelScoped
    @Provides
    fun providesSensorFactory(sensorManager: SensorManager) : SensorFactory {
        return SensorFactory(sensorManager)
    }

    @ViewModelScoped
    @Provides
    fun providesOrientationDataManager(sensorFactory: SensorFactory) : IOrientationDataManager {
        return sensorFactory.getOrientationDataManager()
    }
}
