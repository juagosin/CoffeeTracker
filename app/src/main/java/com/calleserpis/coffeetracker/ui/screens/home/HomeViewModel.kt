package com.calleserpis.coffeetracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.CoffeePhrases
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coffeeUseCase: CoffeeUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()
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
                    _state.update { currentState ->
                        currentState.copy(lastNDaysStats = stats)
                    }

                }
            } catch (e: Exception) {
                // Manejar el error según necesites

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
                        _state.update { currentState ->
                            currentState.copy(timeLastCoffee = it.timestamp)
                        }
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
                _state.update { currentState ->
                    currentState.copy(coffeeCount = coffeeCount)
                }
            }catch (e:Exception){

            }

        }

    }

    private fun loadPhrases() {
        val randomPhrase = CoffeePhrases.phrases.random()
        _state.update { currentState ->
            currentState.copy(randomPhrase = randomPhrase)
        }

    }
}