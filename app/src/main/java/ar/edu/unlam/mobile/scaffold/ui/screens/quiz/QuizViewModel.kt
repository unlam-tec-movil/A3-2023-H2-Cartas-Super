package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.IQuizGameRepository
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizGame
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(repo: IQuizGameRepository) : ViewModel() {

    private lateinit var game: QuizGame

    // CREAR UN REPO DE JUEGOS PARA OBTENER QUIZGAME YA CREADOS

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _heroPortraitUrl = MutableStateFlow("https://loremflickr.com/400/400/cat?lock=1")
    val heroPortraitUrl = _heroPortraitUrl.asStateFlow()

    private val _option1 = MutableStateFlow("option 1")
    val option1 = _option1.asStateFlow()

    private val _option2 = MutableStateFlow("option 2")
    val option2 = _option2.asStateFlow()

    private val _option3 = MutableStateFlow("option 3")
    val option3 = _option3.asStateFlow()

    private val _option4 = MutableStateFlow("option 4")
    val option4 = _option4.asStateFlow()

    private val _showResult = MutableStateFlow(false)
    val showResult = _showResult.asStateFlow()

    private val _isCorrectAnswer = MutableStateFlow(false)
    val isCorrectAnswer = _isCorrectAnswer.asStateFlow()

    init {
        viewModelScope.launch {
            game = withContext(Dispatchers.IO) {
                repo.getNewQuizGame()
            }
            gameInit()
            _isLoading.value = false
        }
    }

    private fun gameInit() {
        _heroPortraitUrl.value = game.correctAnswer.image.url
        _option1.value = game.option1.name
        _option2.value = game.option2.name
        _option3.value = game.option3.name
        _option4.value = game.option4.name
    }

    fun selectOption1() {
        selectOption(QuizOption.OPTION_1)
    }

    fun selectOption2() {
        selectOption(QuizOption.OPTION_2)
    }

    fun selectOption3() {
        selectOption(QuizOption.OPTION_3)
    }

    fun selectOption4() {
        selectOption(QuizOption.OPTION_4)
    }

    private fun selectOption(option: QuizOption) {
        _isCorrectAnswer.value = game.isCorrectAnswer(option)
        _showResult.value = true
    }
}
