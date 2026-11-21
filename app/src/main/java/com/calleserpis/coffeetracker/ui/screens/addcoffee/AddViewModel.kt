package com.calleserpis.coffeetracker.ui.screens.addcoffee

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.AchievementDefinition
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import com.calleserpis.coffeetracker.ui.widget.CoffeeWidgetUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coffeeUseCase: CoffeeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AddCoffeeState())
    private val _lastCoffee = MutableStateFlow<String?>(null)
    val state: StateFlow<AddCoffeeState> = _state.asStateFlow()
    val lastCoffee: StateFlow<String?> = _lastCoffee.asStateFlow()

    init {
        observeLastCoffee()
    }

    private fun observeLastCoffee() {
        viewModelScope.launch {
            coffeeUseCase.getLastCoffeePrefUseCase().collect { coffee ->
                _lastCoffee.value = coffee
            }
        }
    }

    fun onEvent(event: AddEvent) {
        when (event) {
            is AddEvent.OnDateChanged -> {
                _state.update { currentState ->
                    currentState.copy(date = event.value)
                }
            }

            is AddEvent.OnNotesChanged -> {
                _state.update { currentState ->
                    currentState.copy(notes = event.value)
                }
            }

            is AddEvent.OnPriceChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        priceText = event.value,
                        price = event.value.replace(',', '.')
                            .toDoubleOrNull() ?: 0.0
                    )
                }
            }

            AddEvent.SaveCoffee -> {
                saveCoffee()
            }

            is AddEvent.OnCoffeeTypeChanged -> {
                _state.update { currentState ->
                    currentState.copy(type = event.value)
                }
            }
        }
    }

    private fun saveCoffee() {
        _state.update { currentState ->
            currentState.copy(isSaving = true)
        }

        viewModelScope.launch {
            try {
                val currentState = _state.value
                coffeeUseCase.addCoffeeUseCase(
                    Coffee(
                        id = 0,
                        type = currentState.type,
                        timestamp = currentState.date,
                        notes = currentState.notes.ifBlank { null },
                        price = currentState.price.takeIf { it > 0.0 } ?: 0.0
                    )
                )
                coffeeUseCase.saveLastCoffeePrefUseCase(currentState.type.name)
                CoffeeWidgetUpdater.requestUpdate(context)
                checkAchievements()

                _state.update { state ->
                    state.copy(isSaving = false, isSuccess = true)
                }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(isSaving = false, isSuccess = false, error = e.message)
                }
            }
        }
    }

    private suspend fun checkAchievements() {
        val coffeeList = coffeeUseCase.getLastNCoffeesUseCase(10000).first()
        val unlockedAchievements = coffeeUseCase.getAllAchievementsUseCase().first()

        AchievementDefinition.ALL.forEach { achievementDefinition ->
            val isUnlocked = achievementDefinition.checkCondition(coffeeList)
            Log.d(
                "AddViewModel",
                "Checking achievement: ${achievementDefinition.id} : Condicion $isUnlocked"
            )
            if (isUnlocked && unlockedAchievements.none { achievementDefinition.id == it.id }) {
                val newAchievement = Achievement(
                    id = achievementDefinition.id,
                    type = achievementDefinition.type,
                    threshold = achievementDefinition.threshold,
                    unlockedAt = System.currentTimeMillis(),
                    isUnlocked = true
                )

                coffeeUseCase.addAchievementUseCase(newAchievement)
                coffeeUseCase.showNotificationUseCase(
                    context.getString(achievementDefinition.titleRes),
                    context.getString(achievementDefinition.descriptionRes)
                )
            }
        }
    }
}
