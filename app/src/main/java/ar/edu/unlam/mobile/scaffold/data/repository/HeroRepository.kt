package ar.edu.unlam.mobile.scaffold.data.repository

import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.toHero
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.hero.Powerstats
import ar.edu.unlam.mobile.scaffold.domain.hero.toHeroEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HeroRepository @Inject constructor(private val api: HeroService, private val dataBase: HeroDao) :
    IHeroRepository {

    // private val API_COLLECTION_SIZE = 731 // no eliminar
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

    override suspend fun getHero(heroId: Int): DataHero {
        return withContext(Dispatchers.IO) {
            val heroDB = dataBase.getHero(heroId)
            if (heroDB != null) {
                heroDB.toHero()
            } else {
                val hero = api.getHero(heroId)
                val formattedHero = formatDataHero(hero)
                dataBase.insertHero(formattedHero.toHeroEntity())
                formattedHero
            }
        }
    }

    override suspend fun getAllHero(): List<DataHero> {
        val dbList = dataBase.getAll()
        val heroList = mutableListOf<DataHero>()
        val saveToDbList = mutableListOf<HeroEntity>()

        if(dbList.isNotEmpty()) {
            for(i in 1..COLLECTION_MAX_SIZE) {
                val heroDb = dbList.find { it.id == i }
                if (heroDb != null) {
                    heroList.add(heroDb.toHero())
                } else {
                    val heroApi = formatDataHero(api.getHero(i))
                    saveToDbList.add(heroApi.toHeroEntity())
                    heroList.add(heroApi)
                }
            }
        } else {
            for(i in 1..COLLECTION_MAX_SIZE) {
                val heroApi = formatDataHero(api.getHero(i))
                saveToDbList.add(heroApi.toHeroEntity())
                heroList.add(heroApi)
            }
        }

        if (saveToDbList.isNotEmpty()) {
            dataBase.insertAll(saveToDbList)
        }

        return heroList
    }
}
