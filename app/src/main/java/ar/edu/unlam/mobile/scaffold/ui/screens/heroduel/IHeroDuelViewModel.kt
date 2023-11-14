package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import ar.edu.unlam.mobile.scaffold.domain.cardgame.Stat
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.flow.StateFlow

interface IHeroDuelViewModel {
    fun playCard()
    fun selectPlayerCard(cardSelectedIndex: Int)
    fun selectStat(stat: Stat)
    fun useMultiplierX2(use: Boolean)
    fun fight()

    val currentScreen: StateFlow<DuelScreen>
    val isMultiplierAvailable: StateFlow<Boolean>
    val winner: StateFlow<Winner>
    val adversaryScore: StateFlow<Int>
    val playerScore: StateFlow<Int>
    val currentAdversaryCard: StateFlow<HeroModel>
    val currentPlayerCard: StateFlow<HeroModel>
    val currentPlayerDeck: StateFlow<List<HeroModel>>
    val isLoading: StateFlow<Boolean>
}
