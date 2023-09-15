package ar.edu.unlam.mobile.scaffold.repository



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.repository.HeroRepository
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HeroRepositoryTest {

    /*
        JUnit 4 exposes a rule-based API to allow for some automation following the test lifecycle.
        MockK includes a rule which uses this to set up and tear down your mocks without needing to
        manually call MockKAnnotations.init(this).
     */
    @get:Rule
    val mockkRule = MockKRule(this)

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
        @relaxedmockk
        se pueden mockear manualmente los métodos que necesitamos y el resto se genera
        automáticamente.

        @mockk
        debemos generar manualmente una respuesta para todos los métodos.
     */
    @RelaxedMockK
    lateinit var api: HeroService

    @RelaxedMockK
    lateinit var db: HeroDao

    @Test
    fun getHeroFromApiWhenRoomDoesntHaveIt() = runTest {
        val expectedHero = DataHero(id = "1", name = "Mr. Test")
        coEvery { api.getHero(1) } returns expectedHero
        coEvery { db.getHero(1) } returns null

        val repo = HeroRepository(api = api, dataBase = db)
        val hero = repo.getHero(1)

        assertThat(hero).isEqualTo(expectedHero)
    }

    @Test
    fun getHeroFromDatabaseWhenItHasIt() = runTest {
        val expectedHeroEntity = HeroEntity(id = 1, name = "Mr. Test")
        val expectedHero = DataHero(id = "1", name = "Mr. Test")
        coEvery { api.getHero(1) } returns DataHero(id = "2")
        coEvery { db.getHero(1) } returns expectedHeroEntity

        val repo = HeroRepository(api,db)
        val hero = repo.getHero(1)

        assertThat(hero).isEqualTo(expectedHero)
    }
}