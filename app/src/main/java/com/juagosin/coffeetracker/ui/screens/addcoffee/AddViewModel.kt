package com.juagosin.coffeetracker.ui.screens.addcoffee

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.juagosin.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val coffeeUseCase: CoffeeUseCases
) : ViewModel() {
    var state by mutableStateOf(AddCoffeeState())

    fun onEvent(event: AddEvent) {
        when (event){
            is AddEvent.OnDateChanged -> {
                state = state.copy(date = event.value)
            }

            is AddEvent.OnNotesChanged -> {
                state = state.copy(notes = event.value)
            }
        }
    }
}