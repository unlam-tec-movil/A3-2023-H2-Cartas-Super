package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.data.repository.deckrepository.IDeckRepository
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeckViewModelTest {

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

    private lateinit var viewModel: DeckViewModel

    @RelaxedMockK
    lateinit var deckRepository: IDeckRepository

    @RelaxedMockK
    lateinit var heroRepository: IHeroRepository

    private val randomDeck = DeckModel(
        id = null,
        carta1 = HeroModel(id = 10, name = "Mr. Test 10"),
        carta2 = HeroModel(id = 11, name = "Mr. Test 11"),
        carta3 = HeroModel(id = 12, name = "Mr. Test 12"),
        carta4 = HeroModel(id = 13, name = "Mr. Test 13"),
        carta5 = HeroModel(id = 14, name = "Mr. Test 14"),
        carta6 = HeroModel(id = 15, name = "Mr. Test 15")
    )

    @Before
    fun setUp() {
        coEvery { deckRepository.getDeckList() } returns emptyList()
        coEvery { heroRepository.getRandomDeck() } returns randomDeck
        viewModel = DeckViewModel(deckRepository, heroRepository)
    }

    // falta un test así pero que devuelva una lista no vacía.
    @Test
    fun afterViewModelFinishesLoading_VerifyItGetsAllTheUserDecks() = runTest {
        val expectedDeckList: List<DeckModel> = emptyList()

        while (viewModel.isLoading.value) {
            delay(500)
        }
        val deckList = viewModel.listDeck.value

        coVerify(exactly = 1) { deckRepository.getDeckList() }
        assertThat(deckList).containsExactlyElementsIn(expectedDeckList).inOrder()
    }

    @Test
    fun whenUsingGenerarMazoRandom_VerifyRandomDeckReturnsExpectedDeck() = runTest {
        val expectedDeck = DeckModel(
            id = null,
            carta1 = HeroModel(id = 10, name = "Mr. Test 10"),
            carta2 = HeroModel(id = 11, name = "Mr. Test 11"),
            carta3 = HeroModel(id = 12, name = "Mr. Test 12"),
            carta4 = HeroModel(id = 13, name = "Mr. Test 13"),
            carta5 = HeroModel(id = 14, name = "Mr. Test 14"),
            carta6 = HeroModel(id = 15, name = "Mr. Test 15")
        )

        while (viewModel.isLoading.value) {
            delay(500)
        }
        viewModel.generarMazoRandom()
        while (viewModel.isLoading.value) {
            delay(500)
        }
        val deck = viewModel.randomDeck.value

        coVerify(exactly = 1) { heroRepository.getRandomDeck() }
        assertThat(deck).isEqualTo(expectedDeck)
    }

    @Test
    fun afterViewModelFinishesLoading_VerifyTheCurrentScreenIsListaDeMazos() = runTest {
        val expectedCurrentScreen = DeckUI.LISTA_DE_MAZOS

        while (viewModel.isLoading.value) {
            delay(500)
        }
        val currentScreen = viewModel.pantallaActual.value

        assertThat(currentScreen).isEqualTo(expectedCurrentScreen)
    }

    @Test
    fun whenGoingToGenerarMazosAndClickOnGuardarMazo_VerifyViewModelCreatesARandomDeckAndSavesItInDeckRepository() = runTest {
        val expectedDeck = randomDeck
        val expectedScreen = DeckUI.GENERAR_MAZOS

        while (viewModel.isLoading.value) {
            delay(500)
        }
        viewModel.irAGenerarMazos() // ya genera un mazo random
        while (viewModel.isLoading.value) {
            delay(500)
        }
        val currentScreen = viewModel.pantallaActual.value
        val deck = viewModel.randomDeck.value
        viewModel.guardarMazoRandom()

        coVerify(exactly = 1) { deckRepository.insertDeck(expectedDeck) }
        coVerify(exactly = 1) { heroRepository.getRandomDeck() }
        assertThat(deck).isEqualTo(expectedDeck)
        assertThat(currentScreen).isEqualTo(expectedScreen)
    }
}
