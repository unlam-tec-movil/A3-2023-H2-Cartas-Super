package ar.edu.unlam.mobile.scaffold.data.repository

import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizGame

interface IQuizGameRepository {

    suspend fun getNewQuizGame(): QuizGame
}
