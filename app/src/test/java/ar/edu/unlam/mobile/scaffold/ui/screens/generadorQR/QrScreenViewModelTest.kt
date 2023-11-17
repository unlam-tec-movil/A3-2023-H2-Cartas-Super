package ar.edu.unlam.mobile.scaffold.ui.screens.generadorQR

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.screens.qr.QrScreenViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class QrScreenViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var heroRepository: IHeroRepository

    private lateinit var viewModel: QrScreenViewModel

    private val mockHero = HeroModel(id = 123, name = "Mock Hero")

    @Before
    fun setUp() {
        coEvery { heroRepository.getHero(123) } returns mockHero
        viewModel = QrScreenViewModel(heroRepository)
    }
    @Test
    fun alObtenerHeroeVerificarHeroeCargado() = runTest {
        viewModel.getHero(123)

        assertThat(viewModel.isLoading.value).isTrue()
        while (viewModel.isLoading.value) {
            delay(500)
        }

        assertThat(viewModel.hero.value).isEqualTo(mockHero)
        assertThat(viewModel.isLoading.value).isFalse()
    }
    @Test
    fun alIniciarYFinalizarLaCargaVerificarCambioDeLoading() = runTest {
        assertThat(viewModel.isLoading.value).isTrue()

        // Avanza el tiempo para esperar que se complete la corrutina
        advanceTimeBy(500)

        assertThat(viewModel.isLoading.value).isFalse()
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestRule {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(mainThreadSurrogate)
                try {
                    base?.evaluate()
                } finally {
                    Dispatchers.resetMain()
                    mainThreadSurrogate.close()
                }
            }
        }
    }
}
