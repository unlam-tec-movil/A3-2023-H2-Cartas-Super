package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

import ar.edu.unlam.mobile.scaffold.domain.model.DataHero
import kotlinx.coroutines.flow.Flow

interface IHeroRepository {

    suspend fun getRandomPlayerDeck(size: Int): List<DataHero>
    suspend fun getAdversaryDeck(size: Int): List<DataHero>
    suspend fun getHero(heroId: Int): DataHero
    suspend fun getAllHero(): List<DataHero>
    fun preloadHeroCache(): Flow<Float>
}
