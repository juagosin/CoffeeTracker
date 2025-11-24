package com.calleserpis.coffeetracker.ui.screens.editcoffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val coffeeUseCase: CoffeeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(EditCoffeeState())
    val state: StateFlow<EditCoffeeState> = _state.asStateFlow()

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.LoadCoffee -> {
                loadCoffee(event.id)
            }
            is EditEvent.OnDateChanged -> {
                _state.update{ currentState ->
                    currentState.copy(date = event.value)
                }
            }
            is EditEvent.OnPriceChanged -> {
                _state.update{ currentState ->
                    currentState.copy(
                        priceText = event.value,
                        price = event.value.replace(',', '.')
                            .toDoubleOrNull() ?: 0.0
                    )
                }
            }
            is EditEvent.OnCoffeeTypeChanged -> {
                _state.update{ currentState ->
                    currentState.copy(type = event.value)
                }
            }

            EditEvent.SaveCoffee -> {
                saveCoffee()
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
                        id = _state.value.coffee?.id ?: 0,
                        type = _state.value.type,
                        notes = "",
                        timestamp = state.value.date,
                        price = _state.value.price.takeIf { it > 0.0 } ?: 0.0,
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

    private fun loadCoffee(id: Int) {
        var coffee: Coffee
        _state.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {

                coffeeUseCase.getCoffeeByIdUseCase(id).collect { coffee ->
                    coffee?.let {
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                coffee = coffee,
                                type = coffee.type,
                                date = coffee.timestamp,
                                price = coffee.price?.toDouble() ?: 0.0,
                                priceText = coffee.price.toString(),

                            )

                        }

                    }

                }

            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = e.message
                    )

                }
            }
        }
    }

}