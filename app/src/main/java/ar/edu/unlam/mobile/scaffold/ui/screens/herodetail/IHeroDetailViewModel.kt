package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import ar.edu.unlam.mobile.scaffold.core.sensor.IUseSensorData
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.flow.StateFlow

interface IHeroDetailViewModel : IUseSensorData {
    val hero: StateFlow<HeroModel>
    val isLoading: StateFlow<Boolean>

    fun getHero(heroID: Int)
}
