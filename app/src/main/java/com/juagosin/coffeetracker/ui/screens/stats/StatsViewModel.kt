package com.juagosin.coffeetracker.ui.screens.stats

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {
    var state by mutableStateOf(StatsState())
    init {

        loadLastCoffees()
        loadAllHistoryCoffees()
        getCoffeeCount()

        //loadStats()

    }
    private fun loadStats() {
        viewModelScope.launch {
            combine(
                coffeeUseCases.getAllTimeTypeStatsUseCase(),
                coffeeUseCases.getLastNCoffeesUseCase()
            ){allStats, lastCoffees ->
                Pair(allStats, lastCoffees)

            }.collect { (allStats, lastCoffees) ->
                val totalCount = allStats.size

                state = state.copy(
                    allCoffeesStats = allStats,
                    lastNCoffees = lastCoffees,
                    coffeeCount = totalCount
                )

            }
        }
    }
    fun showDeleteDialog(coffee: Coffee) {

        state = state.copy(coffeeToDelete = coffee)

    }

    fun dismissDeleteDialog() {
        state = state.copy(coffeeToDelete = null)

    }

    fun deleteCoffee(id: Int) {
        viewModelScope.launch {
            coffeeUseCases.deleteCoffeeUseCase(id)
        }
        state = state.copy(coffeeToDelete = null)
    }
    private fun getCoffeeCount() {
        var coffeeCount:Int = 0
        viewModelScope.launch {
            try{
                coffeeCount = coffeeUseCases.getCoffeeCount()
                state = state.copy(
                    coffeeCount = coffeeCount
                )
            }catch (e:Exception){

            }

        }

    }

    private fun loadAllHistoryCoffees() {
        viewModelScope.launch {
            try{
                coffeeUseCases.getAllTimeTypeStatsUseCase().collect{

                    state = state.copy(
                        allCoffeesStats = it
                    )

                }



            }catch (e:Exception){

            }
        }
    }

    private fun loadLastCoffees() {
        viewModelScope.launch {
            try{
                coffeeUseCases.getLastNCoffeesUseCase().collect{
                    state = state.copy(
                        lastNCoffees = it
                    )
                }
            }catch (e:Exception){

            }
        }
    }
}