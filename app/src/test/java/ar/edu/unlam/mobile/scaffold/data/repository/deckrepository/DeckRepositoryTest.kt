package ar.edu.unlam.mobile.scaffold.data.repository.deckrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.data.database.dao.DeckDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.DeckEntity
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeckRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var deckDao: DeckDao

    @RelaxedMockK
    lateinit var heroRepository: IHeroRepository

    private lateinit var deckRepository: DeckRepository

    private val deckEntityList: List<DeckEntity> = listOf(
        DeckEntity(id = 1, carta1 = 1, carta2 = 2, carta3 = 3, carta4 = 4, carta5 = 5, carta6 = 6),
        DeckEntity(id = 2, carta1 = 10, carta2 = 20, carta3 = 30, carta4 = 40, carta5 = 50, carta6 = 60),
        DeckEntity(id = 3, carta1 = 15, carta2 = 25, carta3 = 35, carta4 = 45, carta5 = 55, carta6 = 65)
    )

    private val deckModelList = listOf(
        DeckModel(
            id = 1,
            carta1 = HeroModel(id = 1, name = "Mr. Test 1"),
            carta2 = HeroModel(id = 2, name = "Mr. Test 2"),
            carta3 = HeroModel(id = 3, name = "Mr. Test 3"),
            carta4 = HeroModel(id = 4, name = "Mr. Test 4"),
            carta5 = HeroModel(id = 5, name = "Mr. Test 5"),
            carta6 = HeroModel(id = 6, name = "Mr. Test 6")
        ),
        DeckModel(
            id = 2,
            carta1 = HeroModel(id = 10, name = "Mr. Test 10"),
            carta2 = HeroModel(id = 20, name = "Mr. Test 20"),
            carta3 = HeroModel(id = 30, name = "Mr. Test 30"),
            carta4 = HeroModel(id = 40, name = "Mr. Test 40"),
            carta5 = HeroModel(id = 50, name = "Mr. Test 50"),
            carta6 = HeroModel(id = 60, name = "Mr. Test 60")
        ),
        DeckModel(
            id = 3,
            carta1 = HeroModel(id = 15, name = "Mr. Test 15"),
            carta2 = HeroModel(id = 25, name = "Mr. Test 25"),
            carta3 = HeroModel(id = 35, name = "Mr. Test 35"),
            carta4 = HeroModel(id = 45, name = "Mr. Test 45"),
            carta5 = HeroModel(id = 55, name = "Mr. Test 55"),
            carta6 = HeroModel(id = 65, name = "Mr. Test 65")
        )
    )

    private val heroList = listOf(
        HeroModel(id = 1, name = "Mr. Test 1"),
        HeroModel(id = 2, name = "Mr. Test 2"),
        HeroModel(id = 3, name = "Mr. Test 3"),
        HeroModel(id = 4, name = "Mr. Test 4"),
        HeroModel(id = 5, name = "Mr. Test 5"),
        HeroModel(id = 6, name = "Mr. Test 6"),
        HeroModel(id = 10, name = "Mr. Test 10"),
        HeroModel(id = 20, name = "Mr. Test 20"),
        HeroModel(id = 30, name = "Mr. Test 30"),
        HeroModel(id = 40, name = "Mr. Test 40"),
        HeroModel(id = 50, name = "Mr. Test 50"),
        HeroModel(id = 60, name = "Mr. Test 60"),
        HeroModel(id = 15, name = "Mr. Test 15"),
        HeroModel(id = 25, name = "Mr. Test 25"),
        HeroModel(id = 35, name = "Mr. Test 35"),
        HeroModel(id = 45, name = "Mr. Test 45"),
        HeroModel(id = 55, name = "Mr. Test 55"),
        HeroModel(id = 65, name = "Mr. Test 65"),
        HeroModel(id = 100, name = "Mr. Test 100")
    )

    @Before
    fun setUp() {
        coEvery { deckDao.getAll() } returns deckEntityList
        coEvery { heroRepository.getHero(1) } returns heroList.find { it.id == 1 }!!
        coEvery { heroRepository.getHero(2) } returns heroList.find { it.id == 2 }!!
        coEvery { heroRepository.getHero(3) } returns heroList.find { it.id == 3 }!!
        coEvery { heroRepository.getHero(4) } returns heroList.find { it.id == 4 }!!
        coEvery { heroRepository.getHero(5) } returns heroList.find { it.id == 5 }!!
        coEvery { heroRepository.getHero(6) } returns heroList.find { it.id == 6 }!!
        coEvery { heroRepository.getHero(10) } returns heroList.find { it.id == 10 }!!
        coEvery { heroRepository.getHero(20) } returns heroList.find { it.id == 20 }!!
        coEvery { heroRepository.getHero(30) } returns heroList.find { it.id == 30 }!!
        coEvery { heroRepository.getHero(40) } returns heroList.find { it.id == 40 }!!
        coEvery { heroRepository.getHero(50) } returns heroList.find { it.id == 50 }!!
        coEvery { heroRepository.getHero(60) } returns heroList.find { it.id == 60 }!!
        coEvery { heroRepository.getHero(15) } returns heroList.find { it.id == 15 }!!
        coEvery { heroRepository.getHero(25) } returns heroList.find { it.id == 25 }!!
        coEvery { heroRepository.getHero(35) } returns heroList.find { it.id == 35 }!!
        coEvery { heroRepository.getHero(45) } returns heroList.find { it.id == 45 }!!
        coEvery { heroRepository.getHero(55) } returns heroList.find { it.id == 55 }!!
        coEvery { heroRepository.getHero(65) } returns heroList.find { it.id == 65 }!!
        coEvery { heroRepository.getHero(100) } returns heroList.find { it.id == 100 }!!
        deckRepository = DeckRepository(deckDao, heroRepository)
    }

    @Test
    fun whenUsingGetDeckList_VerifyTheRepositoryToReturnTheExpectedDeckList() = runTest {
        val expectedDeckList = deckModelList

        val deckList = deckRepository.getDeckList()

        coVerify(exactly = 1) { deckDao.getAll() }
        assertThat(deckList).containsExactlyElementsIn(expectedDeckList)
    }

    @Test
    fun givenTheProvidedDeck_VerifyTheExpectedDeckEntityIsInsertedInTheDatabase() = runTest {
        val deck = DeckModel(
            id = null,
            carta1 = HeroModel(id = 333, name = "Mr. Test 333"),
            carta2 = HeroModel(id = 123, name = "Mr. Test 123"),
            carta3 = HeroModel(id = 234, name = "Mr. Test 234"),
            carta4 = HeroModel(id = 400, name = "Mr. Test 400"),
            carta5 = HeroModel(id = 500, name = "Mr. Test 500"),
            carta6 = HeroModel(id = 600, name = "Mr. Test 600")
        )
        val expectedDeckEntity = DeckEntity(
            id = null,
            carta1 = deck.carta1.id,
            carta2 = deck.carta2.id,
            carta3 = deck.carta3.id,
            carta4 = deck.carta4.id,
            carta5 = deck.carta5.id,
            carta6 = deck.carta6.id
        )

        deckRepository.insertDeck(deck)

        coVerify(exactly = 1) { deckDao.insertDeck(expectedDeckEntity) }
    }
}
