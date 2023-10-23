package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.flow.Flow

interface IHeroRepository {

    suspend fun getRandomPlayerDeck(size: Int): List<HeroModel>
    suspend fun getAdversaryDeck(size: Int): List<HeroModel>
    suspend fun getHero(heroId: Int): HeroModel
    suspend fun getAllHero(): List<HeroModel>
    fun preloadHeroCache(): Flow<Float>
}
