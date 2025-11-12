package com.calleserpis.coffeetracker.ui.screens.addcoffee

import android.R.attr.type
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import com.calleserpis.coffeetracker.ui.screens.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val coffeeUseCase: CoffeeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AddCoffeeState())
    val state: StateFlow<AddCoffeeState> = _state.asStateFlow()

    fun onEvent(event: AddEvent) {
        when (event){
            is AddEvent.OnDateChanged -> {
                _state.update{ currentState ->
                    currentState.copy(date = event.value)
                }
            }

            is AddEvent.OnNotesChanged -> {
                _state.update{ currentState ->
                    currentState.copy(notes = event.value)
                }
            }
            is AddEvent.OnPriceChanged -> {
                _state.update{ currentState ->
                    currentState.copy(price = event.value.toDouble())
                }
            }

            AddEvent.SaveCoffee -> {
                saveCoffee()
            }

            is AddEvent.OnCoffeeTypeChanged -> {
                _state.update{ currentState ->
                    currentState.copy(type = event.value)
                }
            }
        }
    }

    private fun saveCoffee() {

        _state.update{ currentState ->
            currentState.copy(isSaving = true)
        }
        try{
            viewModelScope.launch {
                coffeeUseCase.addCoffeeUseCase(
                    Coffee(
                        id = 0,
                        type = _state.value.type,
                        timestamp = _state.value.date,
                        notes = _state.value.notes,
                        price = _state.value.price
                    )
                )
            }
            _state.update{ currentState ->
                currentState.copy(isSaving = false, isSuccess = true)
            }

        } catch (e: Exception){
            _state.update{ currentState ->
                currentState.copy(isSaving = false, isSuccess = false, error = e.message)
            }

        }


    }
}