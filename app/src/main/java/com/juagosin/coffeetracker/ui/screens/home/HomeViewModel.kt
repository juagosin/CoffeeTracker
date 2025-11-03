package com.juagosin.coffeetracker.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.CoffeePhrases
import com.juagosin.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coffeeUseCase: CoffeeUseCases
) : ViewModel() {

    var state by mutableStateOf(HomeState())
    init {
        loadPhrases()
        getCoffeeCount()
        getTimeLastCoffee()
        getLastNDaysCoffees()
    }

    private fun getLastNDaysCoffees() {
        viewModelScope.launch {
            try {
                coffeeUseCase.getLastNDaysCoffeeUseCase(7).collect { stats ->
                    state = state.copy(
                        lastNDaysStats = stats
                    )
                }
            } catch (e: Exception) {
                // Manejar el error según necesites
                // Por ejemplo, podrías añadir un campo de error en el state
            }
        }
    }

    private fun getTimeLastCoffee() {
        var coffee: Coffee
        var timeLastCoffee:Long = 0
        viewModelScope.launch {
            try{
                coffeeUseCase.getLasCoffeeUseCase().collect { coffee ->

                    coffee?.let {
                        state = state.copy(
                            timeLastCoffee = it.timestamp
                        )
                    }
                }
            }
            catch (e:Exception){

            }

        }
    }

    private fun getCoffeeCount() {
        var coffeeCount:Int = 0
        viewModelScope.launch {
            try{
                coffeeCount = coffeeUseCase.getCoffeeCount()
                state = state.copy(
                    coffeeCount = coffeeCount
                )
            }catch (e:Exception){

            }

        }

    }

    private fun loadPhrases() {
        val randomPhrase = CoffeePhrases.phrases.random()
        state = state.copy(
            randomPhrase = randomPhrase
        )

    }
}