package ar.edu.unlam.mobile.scaffold.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.IHeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(private val repo: IHeroRepository) : ViewModel() {

    private val _cachingProgress = MutableStateFlow(0.00f)
    val cachingProgress = _cachingProgress.asStateFlow()

    init {
        viewModelScope.launch {
            preloadHeroes()
        }
    }

    private suspend fun preloadHeroes() {
        withContext(Dispatchers.IO) {
            val cachingProgressFlow = repo.preloadHeroCache()
            cachingProgressFlow
                .collect { progress ->
                    _cachingProgress.value = progress
                }
        }
    }
}
