package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.HeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(private val heroRepository: HeroRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _cards = MutableStateFlow<List<HeroModel>>(emptyList())
    val cards: StateFlow<List<HeroModel>> = _cards

    init {
        loadDeck()
    }


    private fun loadDeck() {
        viewModelScope.launch {
            try {
                val existingCards = heroRepository.getSavedCards(6)

                if (existingCards.isNotEmpty()) {

                    _cards.value = existingCards
                } else {

                    val deckSize = 6
                    val newCards = heroRepository.getRandomPlayerDeck(deckSize)


                    heroRepository.saveCardsToDatabase(newCards)

                    _cards.value = newCards
                }

                _isLoading.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Error during deck loading", e)
                _isLoading.value = false
            }
        }
    }

}