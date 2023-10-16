package ar.edu.unlam.mobile.scaffold.domain.cardgame

import ar.edu.unlam.mobile.scaffold.domain.model.DataHero
import ar.edu.unlam.mobile.scaffold.domain.model.Powerstats
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Stat
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Winner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardGame(
    val playerDeck:List<DataHero>,
    val adversaryDeck:List<DataHero>
) {
    private val _winner = MutableStateFlow(Winner.NONE)
    val winner = _winner.asStateFlow()

    private val _canMultix2BeUsed = MutableStateFlow(true)
    val canMultix2BeUsed = _canMultix2BeUsed.asStateFlow()

    private val _playerScore = MutableStateFlow(0)
    val playerScore = _playerScore.asStateFlow()

    private val _adversaryScore = MutableStateFlow(0)
    val adversaryScore = _adversaryScore.asStateFlow()

    private val _currentPlayerDeck = MutableStateFlow(playerDeck)
    val currentPlayerDeck = _currentPlayerDeck.asStateFlow()

    private val _lastCardFightWinner = MutableStateFlow(Winner.NONE)
    val lastCardFightWinner = _lastCardFightWinner.asStateFlow()

    private var currentAdversaryDeck:MutableList<DataHero> = adversaryDeck.toMutableList()

    private val _currentAdversaryCard = MutableStateFlow(adversaryDeck[0])
    val currentAdversaryCard = _currentAdversaryCard.asStateFlow()

    fun playerPlayCard(id: Int, stat: Stat, useMultix2:Boolean) {
        val playerCard = _currentPlayerDeck.value.find { it.id.toInt() == id }
        var playerCardStat = getStat(powerStats = playerCard!!.powerstats,stat = stat)
        val adversaryCardStat = getStat(powerStats = _currentAdversaryCard.value.powerstats,stat = stat)

        playerCardStat = applyMultiply(playerCardStat,useMultix2)

        if (playerCardStat > adversaryCardStat) {
            playerCardWon(playerCardStat,adversaryCardStat)
        }else {
            adversaryCardWon(
                playerCardId = id,
                playerCardStat = playerCardStat,
                adversaryCardStat = adversaryCardStat
            )
        }
    }

    private fun playerCardWon(playerCardStat: Int, adversaryCardStat: Int) {
        _lastCardFightWinner.value = Winner.PLAYER
        _playerScore.value += playerCardStat - adversaryCardStat
        currentAdversaryDeck.remove(_currentAdversaryCard.value)
        if (currentAdversaryDeck.isEmpty()) {
            calculateWinner()
        }else{
            _currentAdversaryCard.value = currentAdversaryDeck[0]
        }
    }

    private fun applyMultiply(playerCardStat: Int, useMultix2: Boolean): Int {
        return if(useMultix2 && _canMultix2BeUsed.value) {
            _canMultix2BeUsed.value = false
            playerCardStat * 2
        }else{
            playerCardStat
        }
    }

    private fun adversaryCardWon(
        playerCardId:Int,
        playerCardStat: Int,
        adversaryCardStat: Int
    ) {
        _lastCardFightWinner.value = Winner.ADVERSARY
        _adversaryScore.value += adversaryCardStat - playerCardStat
        _currentPlayerDeck.value = _currentPlayerDeck.value.filter { it.id.toInt() != playerCardId}
        if (_currentPlayerDeck.value.isEmpty()) {
            calculateWinner()
        }
    }

    private fun calculateWinner() {
        _winner.value = if(_playerScore.value > _adversaryScore.value) {
            Winner.PLAYER
        }else{
            Winner.ADVERSARY
        }
    }

    private fun getStat(powerStats: Powerstats, stat: Stat): Int {
        return when(stat) {
            Stat.COMBAT -> powerStats.combat.toInt()
            Stat.DURABILITY -> powerStats.durability.toInt()
            Stat.INTELLIGENCE -> powerStats.intelligence.toInt()
            Stat.POWER -> powerStats.power.toInt()
            Stat.SPEED -> powerStats.speed.toInt()
            Stat.STRENGTH -> powerStats.strength.toInt()
        }
    }
}