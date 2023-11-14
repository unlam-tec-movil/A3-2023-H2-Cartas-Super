package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import ar.edu.unlam.mobile.scaffold.core.sensor.IUseSensorData
import kotlinx.coroutines.flow.StateFlow

interface IQuizViewModel : IUseSensorData {
    fun selectOption1()
    fun selectOption2()
    fun selectOption3()
    fun selectOption4()
    fun newGame()

    val chosenHero: StateFlow<String>
    val correctAnswer: StateFlow<String>
    val showResult: StateFlow<Boolean>
    val isCorrectAnswer: StateFlow<Boolean>
    val option1: StateFlow<String>
    val option2: StateFlow<String>
    val option3: StateFlow<String>
    val option4: StateFlow<String>
    val heroPortraitUrl: StateFlow<String>
    val isLoading: StateFlow<Boolean>
}
