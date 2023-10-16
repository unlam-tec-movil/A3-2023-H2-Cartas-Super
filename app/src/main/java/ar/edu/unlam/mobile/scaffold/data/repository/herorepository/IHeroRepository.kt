package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

import ar.edu.unlam.mobile.scaffold.data.network.model.HeroApiModel
import kotlinx.coroutines.flow.Flow

interface IHeroRepository {

    suspend fun getRandomPlayerDeck(size: Int): List<HeroApiModel>
    suspend fun getAdversaryDeck(size: Int): List<HeroApiModel>
    suspend fun getHero(heroId: Int): HeroApiModel
    suspend fun getAllHero(): List<HeroApiModel>
    fun preloadHeroCache(): Flow<Float>
}
