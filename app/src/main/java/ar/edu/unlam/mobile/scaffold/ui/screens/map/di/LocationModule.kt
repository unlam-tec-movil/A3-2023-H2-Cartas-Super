package ar.edu.unlam.mobile.scaffold.ui.screens.map.di

import android.content.Context
import ar.edu.unlam.mobile.scaffold.ui.screens.map.data.ILocationService
import ar.edu.unlam.mobile.scaffold.ui.screens.map.data.LocationService
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context
    ): ILocationService = LocationService(
        context,
        LocationServices.getFusedLocationProviderClient(context)
    )
}