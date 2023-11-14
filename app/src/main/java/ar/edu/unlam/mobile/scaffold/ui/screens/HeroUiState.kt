package ar.edu.unlam.mobile.scaffold.ui.screens

import javax.annotation.concurrent.Immutable
@Immutable
sealed interface HeroUiState {
    object Success : HeroUiState
    object Loading : HeroUiState
    object Cancelled : HeroUiState
    object Idle : HeroUiState
    data class Error(val message: String) : HeroUiState
}
