package com.calleserpis.coffeetracker.ui.screens.stats

import android.util.Log
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
class StatsViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(StatsState())
    val state: StateFlow<StatsState> = _state.asStateFlow()
    init {

        loadStats()

    }
    fun onEvent(event: StatsEvent) {
        when (event) {
            is StatsEvent.ToggleCoffeeExpansion -> {
                _state.update { currentState ->
                    currentState.copy(
                        expandedCoffeeId = if (currentState.expandedCoffeeId == event.coffeeId) {
                            null // Colapsar si ya está expandido
                        } else {
                            event.coffeeId // Expandir el nuevo
                        }
                    )
                }
            }
            else -> {}
        }
    }
    private fun loadStats(){
        getCoffeeCount()
        loadLastCoffees()
        loadAllHistoryCoffees()
        loadLast12Months()
        getMoneySpent()

    }
    private fun getMoneySpent() {
        var moneySpent:Double = 0.0
        viewModelScope.launch {
            try{
                moneySpent = coffeeUseCases.getMoneySpent()
                _state.update { currentState ->
                    currentState.copy( moneySpent = moneySpent)
                }
            }catch (e:Exception){

            }

        }
    }

    private fun loadLast12Months() {
        viewModelScope.launch {
            try{
                coffeeUseCases.getLast12MonthsStatsUseCase().collect{
                    _state.update { currentState ->
                    currentState.copy(last12MonthsStats = it)

                    }
                }



            }catch (e:Exception){

            }
        }
    }


    fun showDeleteDialog(coffee: Coffee) {
        _state.update {
            it.copy(coffeeToDelete = coffee)
        }

    }

    fun dismissDeleteDialog() {
        _state.update {
            it.copy(coffeeToDelete = null)
        }

    }

    fun deleteCoffee(id: Int) {
        viewModelScope.launch {
            try{
                coffeeUseCases.deleteCoffeeUseCase(id)
                _state.update {
                    it.copy(coffeeToDelete = null)
                }
                getCoffeeCount()
                getMoneySpent()
            }catch (e: Exception){
                Log.e("StatsViewModel", "Error borrando", e)
            }


        }


    }
    private fun getCoffeeCount() {
        var coffeeCount:Int = 0
        viewModelScope.launch {
            try{
                coffeeCount = coffeeUseCases.getCoffeeCount()
                _state.update { currentState ->
                    currentState.copy( coffeeCount = coffeeCount)
                }

            }catch (e:Exception){

            }

        }

    }

    private fun loadAllHistoryCoffees() {
        viewModelScope.launch {
            try{
                coffeeUseCases.getAllTimeTypeStatsUseCase().collect{
                    _state.update { currentState ->
                        currentState.copy(allCoffeesStats = it)
                    }

                }



            }catch (e:Exception){

            }
        }
    }

    private fun loadLastCoffees() {
        viewModelScope.launch {
            try{
                coffeeUseCases.getLastNCoffeesUseCase().collect{
                    _state.update { currentState ->
                        currentState.copy(lastNCoffees = it)
                    }
                }
            }catch (e:Exception){

            }
        }
    }
}