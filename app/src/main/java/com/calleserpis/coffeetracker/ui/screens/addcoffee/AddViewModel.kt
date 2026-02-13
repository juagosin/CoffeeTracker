package com.calleserpis.coffeetracker.ui.screens.addcoffee

import android.R
import android.content.Context
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.AchievementDefinition
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.`-DeprecatedOkio`.source
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

            AchievementDefinition.ALL.forEach { achievementDefinition ->
                Log.d(
                    "AddViewModel",
                    "Achievement check condition: ${achievementDefinition.checkCondition(coffeeList)}"
                )
                if (achievementDefinition.checkCondition(coffeeList)) {
                    Log.d("AddViewModel", "AllUnlockedAchievements: ${AllUnlockedAchievements}")
                    Log.d("AddViewModel", "Checking achievement: ${achievementDefinition.id}")

                    Log.d(
                        "AddViewModel",
                        "Condicion: ${AllUnlockedAchievements.none { achievementDefinition.id == it.id }}"
                    )


                    if ((AllUnlockedAchievements.isEmpty()) || (AllUnlockedAchievements.none  { achievementDefinition.id == it.id })) {

                        Log.d(
                            "AddViewModel",
                            "Entra en el if: AllUnlockedAchievements: ${achievementDefinition.id}"
                        )
                        var newAchievement: Achievement = Achievement(
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

    }
}