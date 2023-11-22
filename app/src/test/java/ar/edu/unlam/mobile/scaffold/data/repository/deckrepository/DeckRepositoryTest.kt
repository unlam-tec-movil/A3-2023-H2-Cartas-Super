package ar.edu.unlam.mobile.scaffold.data.repository.deckrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.DeckEntity
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
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
    lateinit var deckDao: HeroDao

    @RelaxedMockK
    lateinit var heroRepository: IHeroRepository

    private lateinit var deckRepository: DeckRepository

    val deckEntityList: List<DeckEntity> = listOf(
        DeckEntity(
            id = 1,
            carta1 = 1,
            carta2 = 2,
            carta3 = 3,
            carta4 = 4,
            carta5 = 5,
            carta6 = 6
        ),
        DeckEntity(
            id = 2,
            carta1 = 10,
            carta2 = 20,
            carta3 = 30,
            carta4 = 40,
            carta5 = 50,
            carta6 = 60
        ),
        DeckEntity(
            id = 3,
            carta1 = 15,
            carta2 = 25,
            carta3 = 35,
            carta4 = 45,
            carta5 = 55,
            carta6 = 65
        )
    )
    @Before
    fun setUp() {
        coEvery { deckDao.getAll() }
    }

    @Test
    fun getDeckList() {
    }

    @Test
    fun insertDeck() {
    }
}
