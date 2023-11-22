package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.data.repository.deckrepository.IDeckRepository
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@ExperimentalCoroutinesApi
class DeckViewModelTest {

    // Reglas para pruebas
    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks para repositorios
    @RelaxedMockK
    lateinit var deckRepository: IDeckRepository

    @RelaxedMockK
    lateinit var heroRepository: IHeroRepository

    // ViewModel a probar
    private lateinit var viewModel: DeckViewModel

    // Lista mock de mazos
    private val mockDeckList = listOf(
        DeckModel(id = 1),
        DeckModel(id = 2),
        DeckModel(id = 3)
    )

    @Before
    fun setUp() {
        // Mock de llamada a repositorios
        coEvery { deckRepository.getDeckList() } returns mockDeckList
        coEvery { heroRepository.getRandomDeck() } returns DeckModel(id = 4)

        // Inicialización del ViewModel con los repositorios simulados
        viewModel = DeckViewModel(deckRepository, heroRepository)
    }

    @Test
    fun DespuesDeQueElViewModelFinaliceLaCargaVerificarQueLaListaDeMazosNoEsteVacia() = runTest {
        // Espera hasta que isLoading sea falso (carga finalizada)
        while (viewModel.isLoading.value) {
            delay(500)
        }

        // Obtiene la lista de mazos del ViewModel
        val listDeck = viewModel.listDeck.value

        // Verifica que la lista de mazos no esté vacía
        assertThat(listDeck).isNotEmpty()
    }

    @Test
    fun despuesDeCargarViewModelVerificarMazoAleatorioGenerado() = runTest {
        // Espera hasta que isLoading sea falso (carga finalizada)
        while (viewModel.isLoading.value) {
            delay(500)
        }

        // Obtiene el mazo aleatorio generado por el ViewModel
        val randomDeck = viewModel.randomDeck.value

        // Verifica que el mazo aleatorio no sea nulo
        assertThat(randomDeck).isNotNull()
    }

    @Test
    fun despuesDeCargarElViewModelVerificarSiLaCargaEsFalsa() = runTest {
        // Espera hasta que isLoading sea falso (carga finalizada)
        while (viewModel.isLoading.value) {
            delay(500)
        }

        // Obtiene el estado de isLoading del ViewModel
        val isLoading = viewModel.isLoading.value

        // Verifica que isLoading sea falso (carga finalizada)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun alGuardarMazoAleatorioVerificarLlamadaAlRepositorio() = runTest {
        viewModel.guardarMazoRandom()
        // coVerify verifica llamadas a métodos en mocks dentro de un contexto de coroutines
        coVerify { deckRepository.insertDeck(any()) }
    }

    @Test
    fun alObtenerTodosLosMazosVerificarLlamadaAlRepositorio() = runTest {
        viewModel.obtenerTodosLosMazos()

        coVerify { deckRepository.getDeckList() }
    }

    @Test
    fun alIrAListaDeMazosVerificarCambioDePantalla() {
        viewModel.irAListaDeMazos()

        assertThat(viewModel.pantallaActual.value).isEqualTo(DeckUI.LISTA_DE_MAZOS)
    }

    @Test
    fun alIrAGenerarMazosVerificarCambioDePantalla() {
        viewModel.irAGenerarMazos()

        assertThat(viewModel.pantallaActual.value).isEqualTo(DeckUI.GENERAR_MAZOS)
    }
}
