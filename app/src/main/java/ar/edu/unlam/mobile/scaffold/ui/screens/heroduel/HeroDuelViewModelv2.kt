package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.GameRepository
import ar.edu.unlam.mobile.scaffold.domain.cardgame.CardGame
import ar.edu.unlam.mobile.scaffold.domain.model.DataHero
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Stat
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Winner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDuelViewModelv2 @Inject constructor(private val repo: GameRepository) : ViewModel() {

    private val _currentPlayerCard = MutableStateFlow(DataHero())
    val currentPlayerCard = _currentPlayerCard.asStateFlow()

    private val _cardSelectedIndex = MutableStateFlow(0)
    val cardSelectedIndex = _cardSelectedIndex.asStateFlow()

    private val _showWinnerScreen = MutableStateFlow(false)
    val showWinnerScreen = _showWinnerScreen.asStateFlow()

    private val _showHeroDuelScreen = MutableStateFlow(false)
    val showHeroDuelScreen = _showHeroDuelScreen.asStateFlow()

    private lateinit var game: CardGame

    lateinit var winner: StateFlow<Winner>
        private set

    lateinit var canMultix2BeUsed: StateFlow<Boolean>
        private set

    private var useMultix2 = false

    private var selectedStat = Stat.POWER

    lateinit var playerScore: StateFlow<Int>
        private set

    lateinit var adversaryScore: StateFlow<Int>
        private set

    lateinit var currentPlayerDeck: StateFlow<List<DataHero>>
        private set

    lateinit var currentAdversaryCard: StateFlow<DataHero>
        private set

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showSelectCardScreen = MutableStateFlow(true)
    val showSelectCardScreen = _showSelectCardScreen.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            game = repo.getNewCardGame()
            initStateFlows()
            _isLoading.value = false
        }
    }

    private fun initStateFlows() {
        winner = game.winner.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Winner.NONE
        )
        canMultix2BeUsed = game.canMultix2BeUsed.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true
        )
        playerScore = game.playerScore.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )
        adversaryScore = game.adversaryScore.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )
        currentPlayerDeck = game.currentPlayerDeck.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf(DataHero(), DataHero(), DataHero())
        )
        currentAdversaryCard = game.currentAdversaryCard.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DataHero()
        )
    }

    fun onPlayCardClick() {
        _showSelectCardScreen.value = false
        _showHeroDuelScreen.value = true
    }

    fun onPlayerCardClick(cardSelectedIndex: Int) {
        _cardSelectedIndex.value = cardSelectedIndex
        _currentPlayerCard.value = currentPlayerDeck.value[cardSelectedIndex]
    }

    fun onClickSelectedStat(stat: Stat) {
        selectedStat = stat
    }

    fun useMultiplierX2(use: Boolean) {
        useMultix2 = use
    }

    fun onFightClick() {
        val cardId = _currentPlayerCard.value.id.toInt()
        game.playerPlayCard(cardId, selectedStat, useMultix2)
        useMultix2 = false
        if (winner.value == Winner.NONE) {
            if (game.lastCardFightWinner.value == Winner.ADVERSARY) {
                _showHeroDuelScreen.value = false
                onPlayerCardClick(0)
                _showSelectCardScreen.value = true
            }
        } else {
            _showHeroDuelScreen.value = false
            _showSelectCardScreen.value = false
            _showWinnerScreen.value = true
        }
    }
}
