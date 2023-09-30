package ar.edu.unlam.mobile.scaffold.data.repository


import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.hero.Powerstats
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeroRepositoryManualTest @Inject constructor(): IHeroRepository {

    private fun heroListTest1() : List<DataHero> {
        val hero1 = DataHero(
            name = "test1",
            powerstats = Powerstats(
                combat = "10",
                durability = "20",
                intelligence = "30",
                power = "40",
                speed = "50",
                strength = "60"
            )
        )
        val hero2 = DataHero(
            name = "test2",
            powerstats = Powerstats(
                combat = "100",
                durability = "200",
                intelligence = "300",
                power = "400",
                speed = "500",
                strength = "600"
            )
        )
        val hero3 = DataHero(
            name = "test3",
            powerstats = Powerstats(
                combat = "1",
                durability = "2",
                intelligence = "3",
                power = "4",
                speed = "5",
                strength = "6"
            )
        )
        return listOf(hero1,hero2,hero3)
    }
    private fun heroListTest2() : List<DataHero> {
        val hero1 = DataHero(
            name = "test4",
            powerstats = Powerstats(
                combat = "800",
                durability = "30",
                intelligence = "200",
                power = "100",
                speed = "50",
                strength = "60"
            )
        )
        val hero2 = DataHero(
            name = "test5",
            powerstats = Powerstats(
                combat = "900",
                durability = "50",
                intelligence = "40",
                power = "30",
                speed = "20",
                strength = "10"
            )
        )
        val hero3 = DataHero(
            name = "test6",
            powerstats = Powerstats(
                combat = "999",
                durability = "25",
                intelligence = "20",
                power = "20",
                speed = "60",
                strength = "250"
            )
        )
        return listOf(hero1,hero2,hero3)
    }


    override suspend fun getAdversaryDeck(size: Int): List<DataHero> {
        return heroListTest2()
    }

    override suspend fun getRandomPlayerDeck(size: Int): List<DataHero> {
        return heroListTest1()
    }


    override suspend fun getHero(heroId: Int): DataHero {
        return DataHero(id = heroId.toString())
    }

    override suspend fun getAllHero(): List<DataHero> {
        val dataHeroTestList = mutableListOf<DataHero>()
        for(i in 1..731) {
            dataHeroTestList.add(
                DataHero(name = "Test $i", id = "$i", isFavorite = i%3 == 0)
            )
        }
        return dataHeroTestList
    }

    override fun preloadHeroCache(): Flow<Float> {
        TODO("Not yet implemented")
    }

}