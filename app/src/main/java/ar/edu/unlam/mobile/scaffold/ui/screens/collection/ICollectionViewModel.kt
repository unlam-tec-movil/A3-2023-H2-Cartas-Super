package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import ar.edu.unlam.mobile.scaffold.core.sensor.IUseSensorData
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.flow.StateFlow

interface ICollectionViewModel : IUseSensorData {

    val heroList: StateFlow<List<HeroModel>>
    val isLoading: StateFlow<Boolean>
}
