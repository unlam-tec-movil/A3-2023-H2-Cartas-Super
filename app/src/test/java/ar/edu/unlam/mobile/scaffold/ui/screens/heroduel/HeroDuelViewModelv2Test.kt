package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.data.repository.GameRepository
import ar.edu.unlam.mobile.scaffold.domain.cardgame.CardGame
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HeroDuelViewModelv2Test {

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
    lateinit var gameRepository: GameRepository

    private lateinit var viewModel: HeroDuelViewModelv2

    private val adversaryDeck = listOf(
        HeroModel(
            id = 1,
            name = "Adversary 1",
            stats = StatModel(
                combat = 10,
                durability = 20,
                intelligence = 30,
                power = 40,
                speed = 50,
                strength = 60
            )
        ),
        HeroModel(
            id = 2,
            name = "Adversary 2",
            stats = StatModel(
                combat = 20,
                durability = 30,
                intelligence = 40,
                power = 50,
                speed = 60,
                strength = 70
            )
        ),
        HeroModel(
            id = 3,
            name = "Adversary 3",
            stats = StatModel(
                combat = 100,
                durability = 20,
                intelligence = 30,
                power = 40,
                speed = 50,
                strength = 60
            )
        )
    )

    private val playerDeck = listOf(
        HeroModel(
            id = 4,
            name = "Player 1",
            stats = StatModel(
                combat = 10,
                durability = 100,
                intelligence = 30,
                power = 40,
                speed = 50,
                strength = 60
            )
        ),
        HeroModel(
            id = 5,
            name = "Player 2",
            stats = StatModel(
                combat = 20,
                durability = 30,
                intelligence = 100,
                power = 50,
                speed = 60,
                strength = 70
            )
        ),
        HeroModel(
            id = 6,
            name = "Player 3",
            stats = StatModel(
                combat = 100,
                durability = 20,
                intelligence = 30,
                power = 100,
                speed = 50,
                strength = 60
            )
        )
    )

    @Before
    fun setUp() {
        coEvery { gameRepository.getNewCardGame() } returns CardGame(
            playerDeck = playerDeck,
            adversaryDeck = adversaryDeck
        )
        viewModel = HeroDuelViewModelv2(gameRepository)
    }

    @Test
    fun afterViewModelFinishesLoading_VerifyCurrentPlayerCardIsTheFirstHeroOfPlayerDeck() = runTest {
        val expectedPlayerCard = playerDeck[0]
        while (viewModel.isLoading.value) {}

        val currentPlayerCard = viewModel.currentPlayerCard.value

        assertThat(currentPlayerCard).isEqualTo(expectedPlayerCard)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun afterViewModelFinishesLoading_VerifyWinnerIsSetToNone() = runTest {
        val expectedWinner = Winner.NONE
        while (viewModel.isLoading.value) {}

        // Create an empty collector for the StateFlow. StateFlows creados con StateIn necesitan un
        // subscriptor para que empiecen a recolectar información.
        // https://developer.android.com/kotlin/flow/test#statein
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.winner.collect()
        }
        val winner = viewModel.winner.value

        assertThat(winner).isEqualTo(expectedWinner)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun afterViewModelFinishesLoading_VerifyMultiplierIsAvailable() = runTest {
        val expectedValue = true
        while (viewModel.isLoading.value) {}
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.isMultiplierAvailable.collect()
        }
        val isMultiplierAvailable = viewModel.isMultiplierAvailable.value

        assertThat(isMultiplierAvailable).isEqualTo(expectedValue)
    }

    @Test
    fun getPlayerScore() {
    }

    @Test
    fun getAdversaryScore() {
    }

    @Test
    fun getCurrentPlayerDeck() {
    }

    @Test
    fun getCurrentAdversaryCard() {
    }

    @Test
    fun getCurrentScreen() {
    }

    @Test
    fun isLoading() {
    }

    @Test
    fun onPlayCardClick() {
    }

    @Test
    fun selectPlayerCard() {
    }

    @Test
    fun onClickSelectedStat() {
    }

    @Test
    fun useMultiplierX2() {
    }

    @Test
    fun onFightClick() {
    }
}
