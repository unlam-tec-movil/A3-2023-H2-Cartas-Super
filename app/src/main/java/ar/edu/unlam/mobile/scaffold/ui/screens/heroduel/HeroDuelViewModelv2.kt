package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.gamerepository.IGameRepository
import ar.edu.unlam.mobile.scaffold.domain.cardgame.CardGame
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Stat
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDuelViewModelv2 @Inject constructor(
    private val repo: IGameRepository
) : ViewModel(), IHeroDuelViewModel {

    private val _currentPlayerCard = MutableStateFlow(HeroModel())
    override val currentPlayerCard = _currentPlayerCard.asStateFlow()

    private lateinit var game: CardGame

    override lateinit var winner: StateFlow<Winner>
        private set

    override lateinit var isMultiplierAvailable: StateFlow<Boolean>
        private set

    private var useMultix2 = false

    private var selectedStat = Stat.POWER

    override lateinit var playerScore: StateFlow<Int>
        private set

    override lateinit var adversaryScore: StateFlow<Int>
        private set

    override lateinit var currentPlayerDeck: StateFlow<List<HeroModel>>
        private set

    override lateinit var currentAdversaryCard: StateFlow<HeroModel>
        private set

    private val _currentScreen = MutableStateFlow(DuelScreen.SELECT_CARD_UI)
    override val currentScreen = _currentScreen.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            game = repo.getNewCardGame()
            initStateFlows()
            _isLoading.value = false
        }
    }

    private suspend fun initStateFlows() {
        winner = game.winner.stateIn(scope = viewModelScope)
        isMultiplierAvailable = game.canMultix2BeUsed.stateIn(scope = viewModelScope)
        playerScore = game.playerScore.stateIn(scope = viewModelScope)
        adversaryScore = game.adversaryScore.stateIn(scope = viewModelScope)
        currentPlayerDeck = game.currentPlayerDeck.stateIn(scope = viewModelScope)
        _currentPlayerCard.value = currentPlayerDeck.value[0]
        currentAdversaryCard = game.currentAdversaryCard.stateIn(scope = viewModelScope)
    }

    override fun playCard() {
        _currentScreen.value = DuelScreen.DUEL_UI
    }

    override fun selectPlayerCard(cardSelectedIndex: Int) {
        _currentPlayerCard.value = currentPlayerDeck.value[cardSelectedIndex]
    }

    override fun selectStat(stat: Stat) {
        selectedStat = stat
    }

    override fun useMultiplierX2(use: Boolean) {
        useMultix2 = use
    }

    override fun fight() {
        val cardId = _currentPlayerCard.value.id
        game.playerPlayCard(cardId, selectedStat, useMultix2)
        useMultix2 = false
        if (winner.value == Winner.NONE) {
            if (game.lastCardFightWinner.value == Winner.ADVERSARY) {
                // se necesita resetear el estado, por eso se llama a este método con parámetro 0
                selectPlayerCard(0)
                _currentScreen.value = DuelScreen.SELECT_CARD_UI
            }
        } else {
            _currentScreen.value = DuelScreen.FINISHED_DUEL_UI
        }
    }
}
