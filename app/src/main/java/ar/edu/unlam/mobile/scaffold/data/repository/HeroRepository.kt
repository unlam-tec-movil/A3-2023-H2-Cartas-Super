package ar.edu.unlam.mobile.scaffold.data.repository

import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.hero.Powerstats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HeroRepository @Inject constructor(private val api: HeroService, private val dataBase: HeroDao) :
    IHeroRepository {

    private val API_COLLECTION_SIZE = 731
    private val COLLECTION_MAX_SIZE = 50 // es menor o igual a API_COLLECTION_SIZE

    override suspend fun getAdversaryDeck(size:Int): List<DataHero> {
        return getRandomPlayerDeck(size)
    }



    override suspend fun getRandomPlayerDeck(size:Int): List<DataHero> {
        val list = mutableListOf<DataHero>()
        return withContext(Dispatchers.IO) {
            for (i in 1..size) {
                val randomId = (1..COLLECTION_MAX_SIZE).random()
                val hero = getHero(randomId)
                list.add(hero)
            }
            list
        }
    }
    private fun formatDataHero(h: DataHero): DataHero {
        return if(isPowerStatsNull(h)) convertNullPowerstatsToNotNull(h) else h
    }
    private fun isPowerStatsNull(h: DataHero):Boolean {
        return h.powerstats.power == "null"
    }

    private fun convertNullPowerstatsToNotNull(h: DataHero): DataHero {
        return h.copy(
            powerstats = Powerstats(
                combat = "1",
                durability = "1",
                intelligence = "1",
                power = "1",
                speed = "1",
                strength = "1"
            )
        )
    }

    /*
    override suspend fun getHero(heroId: Int): DataHero {
        var hero: DataHero? = null
        val call = RetrofitHelperClaseAEliminar.apiService.getHero(heroId.toString())
        val job = coroutineScope {
            launch(Dispatchers.IO) {
                hero = call.execute().body()
            }
        }
        job.join()
        return hero ?: DataHero()
    }
    */
    override suspend fun getHero(heroId: Int): DataHero {
        return withContext(Dispatchers.IO) {
            val heroDB = dataBase.getHero(heroId)
            if (heroDB != null) {
                DataHeroConverter.heroEntityToDataHero(heroDB)
            } else {
                val heroApi = formatDataHero(api.getHero(heroId))
                apiDataSaveToDB(heroApi)
                heroApi
            }
        }
    }

    override suspend fun getAllHero(): List<DataHero> {
        val list = mutableListOf<DataHero>()
        return withContext(Dispatchers.IO) {
            for (i in 1..COLLECTION_MAX_SIZE) {
                val hero = getHero(i)
                list.add(hero)
            }
            list
        }
    }

    private suspend fun apiDataSaveToDB(h: DataHero) {
        val dataHero: DataHero = formatDataHero(h)
        val hero: HeroEntity = DataHeroConverter.dataHeroToHeroEntity(dataHero)
        dataBase.insertHero(hero)
    }
}