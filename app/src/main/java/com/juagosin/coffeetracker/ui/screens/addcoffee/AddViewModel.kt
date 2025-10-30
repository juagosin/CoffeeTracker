package com.juagosin.coffeetracker.ui.screens.addcoffee

import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

            AddEvent.SaveCoffee -> {
                saveCoffee()
            }

            is AddEvent.OnCoffeeTypeChanged -> {
                state = state.copy(type = event.value)
            }
        }
    }

    private fun saveCoffee() {
        state = state.copy(isSaving = true)
        try{
            viewModelScope.launch {
                coffeeUseCase.addCoffeeUseCase(
                    Coffee(
                        id = 0,
                        type = state.type,
                        timestamp = state.date,
                        notes = state.notes
                    )
                )
            }

            state = state.copy(isSaving = false)
            state = state.copy(isSuccess = true)

        } catch (e: Exception){
            state = state.copy(isSuccess = false)
            state = state.copy(isSaving = false)
            state = state.copy(error = e.message)

        }


    }
}