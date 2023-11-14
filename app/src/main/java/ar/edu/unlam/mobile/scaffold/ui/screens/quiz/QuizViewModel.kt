package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.IQuizGameRepository
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizGame
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repo: IQuizGameRepository,
    private val orientationDataManager: IOrientationDataManager
) : ViewModel(), IQuizViewModel {

    private lateinit var game: QuizGame

    private val _sensorData = MutableStateFlow(SensorData())
    override val sensorData = _sensorData.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    override val isLoading = _isLoading.asStateFlow()

    private val _heroPortraitUrl = MutableStateFlow("https://loremflickr.com/400/400/cat?lock=1")
    override val heroPortraitUrl = _heroPortraitUrl.asStateFlow()

    private val _option1 = MutableStateFlow("option 1")
    override val option1 = _option1.asStateFlow()

    private val _option2 = MutableStateFlow("option 2")
    override val option2 = _option2.asStateFlow()

    private val _option3 = MutableStateFlow("option 3")
    override val option3 = _option3.asStateFlow()

    private val _option4 = MutableStateFlow("option 4")
    override val option4 = _option4.asStateFlow()

    private val _showResult = MutableStateFlow(false)
    override val showResult = _showResult.asStateFlow()

    private val _isCorrectAnswer = MutableStateFlow(false)
    override val isCorrectAnswer = _isCorrectAnswer.asStateFlow()

    private val _correctAnswer = MutableStateFlow("Correct Hero Name")
    override val correctAnswer = _correctAnswer.asStateFlow()

    private val _chosenHero = MutableStateFlow("Chosen hero name")
    override val chosenHero = _chosenHero.asStateFlow()

    init {
        viewModelScope.launch {
            orientationDataManager.getSensorData().collect {
                _sensorData.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            gameInit()
        }
    }

    override fun newGame() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _showResult.value = false
            gameInit()
        }
    }

    private suspend fun gameInit() {
        game = repo.getNewQuizGame()
        _heroPortraitUrl.value = game.correctAnswer.image.url
        _option1.value = game.option1.name
        _option2.value = game.option2.name
        _option3.value = game.option3.name
        _option4.value = game.option4.name
        _correctAnswer.value = game.correctAnswer.name
        _isLoading.value = false
    }

    override fun selectOption1() {
        selectOption(QuizOption.OPTION_1)
    }

    override fun selectOption2() {
        selectOption(QuizOption.OPTION_2)
    }

    override fun selectOption3() {
        selectOption(QuizOption.OPTION_3)
    }

    override fun selectOption4() {
        selectOption(QuizOption.OPTION_4)
    }

    private fun selectOption(option: QuizOption) {
        _isCorrectAnswer.value = game.isCorrectAnswer(option)
        _chosenHero.value = game.selectedAnswer
        _showResult.value = true
    }

    override fun cancelSensorDataFlow() {
        orientationDataManager.cancel()
    }
    override fun onCleared() {
        super.onCleared()
        orientationDataManager.cancel()
    }
}
