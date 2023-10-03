package ar.edu.unlam.mobile.scaffold.data.repository

import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.cardgame.CardGame
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameRepository @Inject constructor(private val repo: IHeroRepository) {

    private val DEFAULT_DECK_SIZE = 3
    /*
    suspend fun getNewCardGame():CardGame {
        val adversaryDeck = repo.getRandomPlayerDeck(3)
        val playerDeck = repo.getRandomPlayerDeck(3)
        return CardGame(playerDeck = playerDeck, adversaryDeck = adversaryDeck)
    }

     */

    suspend fun getNewCardGame(): CardGame {
        var playerDeck: List<DataHero> = listOf()
        var adversaryDeck: List<DataHero> = listOf()

        CoroutineScope(Dispatchers.IO).launch {
            val playerDeckJob = async { repo.getRandomPlayerDeck(DEFAULT_DECK_SIZE) }
            val adversaryDeckJob = async { repo.getAdversaryDeck(DEFAULT_DECK_SIZE) }
            playerDeck = playerDeckJob.await()
            adversaryDeck = adversaryDeckJob.await()
        }.join()

        return CardGame(playerDeck, adversaryDeck)
    }
}
