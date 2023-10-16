package ar.edu.unlam.mobile.scaffold.heroDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.ui.screens.herodetail.HeroDetailViewModelImp
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HeroDetailViewModelImpTest {

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
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // leer la descripción de la clase

    /*
        @relaxedmockk
        se pueden mockear manualmente los métodos que necesitamos y el resto se genera
        automáticamente.

        @mockk
        debemos generar manualmente una respuesta para todos los métodos.
     */

    @RelaxedMockK
    lateinit var repo: IHeroRepository

    @RelaxedMockK
    lateinit var sensorDataManager: IOrientationDataManager

    lateinit var viewModel: HeroDetailViewModelImp

    @Before
    fun setUp() {
        viewModel = HeroDetailViewModelImp(repo, sensorDataManager)
    }

    // runTest es una corutina para testing, lo que permite usar funciones suspend
    // coEvery es lo mismo que un Every pero permite dar comportamiento a funciones suspend
    @Test
    fun whenPassingAnId_returnTheCorrectHero() = runTest {
        val expectedHero = DataHero(id = "1", name = "Mr. Test")
        coEvery { repo.getHero(1) } returns expectedHero

        viewModel.getHero(1)
        val isLoading = viewModel.isLoading
        while (isLoading.value) {
            delay(100L)
        }
        val hero = viewModel.hero.value

        assertThat(hero).isEqualTo(expectedHero)
    }
}
