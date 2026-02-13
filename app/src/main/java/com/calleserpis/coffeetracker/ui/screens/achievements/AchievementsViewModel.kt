package com.calleserpis.coffeetracker.ui.screens.achievements

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import com.calleserpis.coffeetracker.ui.screens.stats.StatsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementsState())
    val state: StateFlow<AchievementsState> = _state.asStateFlow()

    init{
        loadAchievements()
    }
    private fun loadAchievements() {
        viewModelScope.launch {
            try {
                coffeeUseCases.getAllAchievementsUseCase().collect {
                    _state.update { currentState ->
                        currentState.copy(allAchievements = it)
                    }
                }
            } catch (e: Exception){
                Log.e("AchievementsViewModel", "Error loading achievements", e)
            }
        }

    }


}