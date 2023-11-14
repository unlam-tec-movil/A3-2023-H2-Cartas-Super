package ar.edu.unlam.mobile.scaffold.ui.screens.home

import ar.edu.unlam.mobile.scaffold.core.sensor.IUseSensorData
import kotlinx.coroutines.flow.StateFlow

interface IHomeViewModel : IUseSensorData {

    val cachingProgress: StateFlow<Float>
}
