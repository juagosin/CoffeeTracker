package com.calleserpis.coffeetracker.ui.screens.addcoffee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.AchievementDefinition
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
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
        try {
            viewModelScope.launch {

                coffeeUseCase.addCoffeeUseCase(
                    Coffee(
                        id = 0,
                        type = _state.value.type,
                        timestamp = _state.value.date,
                        notes = _state.value.notes,
                        price = _state.value.price.takeIf { it > 0.0 } ?: 0.0
                    )
                )
                coffeeUseCase.saveLastCoffeePrefUseCase(_state.value.type.name)

                checkAchievements()
            }
            _state.update { currentState ->
                currentState.copy(isSaving = false, isSuccess = true)
            }

        } catch (e: Exception) {
            _state.update { currentState ->
                currentState.copy(isSaving = false, isSuccess = false, error = e.message)
            }

        }


    }

    private fun checkAchievements() {
        viewModelScope.launch {
            val coffees: Flow<List<Coffee>> = coffeeUseCase.getLastNCoffeesUseCase(10000)
            val coffeeList: List<Coffee> = coffees.first()
            val AllAchievements = coffeeUseCase.getAllAchievementsUseCase()
            val AllUnlockedAchievements = AllAchievements.first()

            AchievementDefinition.ALL.forEach {
                Log.d(
                    "AddViewModel",
                    "Achievement check condition: ${it.checkCondition(coffeeList)}"
                )
                if (it.checkCondition(coffeeList)) {
                    Log.d("AddViewModel", "Checking achievement: ${it.id}")
                    Log.d("AddViewModel", "Achievement check condition: ${coffeeList}")
                    if ((AllUnlockedAchievements.isEmpty()) || (!AllUnlockedAchievements.any { it.id == it.id })) {
                        Log.d("AddViewModel", "AllUnlockedAchievements: ${AllUnlockedAchievements}")
                        var newAchievement: Achievement = Achievement(
                            id = it.id,
                            type = it.type,
                            threshold = it.threshold,
                            unlockedAt = System.currentTimeMillis(),
                            isUnlocked = true

                            )
                        coffeeUseCase.addAchievementUseCase(newAchievement)


                    }


                }
            }
        }

    }
}