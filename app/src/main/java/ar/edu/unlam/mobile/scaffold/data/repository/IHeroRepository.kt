package ar.edu.unlam.mobile.scaffold.data.repository

import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero

interface IHeroRepository {

    suspend fun getRandomPlayerDeck(size:Int): List<DataHero>
    suspend fun getAdversaryDeck(size:Int): List<DataHero>
    suspend fun getHero(heroId:Int): DataHero
    suspend fun getAllHero():List<DataHero>
}