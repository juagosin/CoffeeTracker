package com.juagosin.coffeetracker.ui.screens.stats

import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juagosin.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {
    var state by mutableStateOf(StatsState())
    init {
        Log.d("StatsViewModel", "coffeeCount = ${state.coffeeCount}")
        loadLastCoffees()
        loadAllHistoryCoffees()

    }

    private fun loadAllHistoryCoffees() {
        viewModelScope.launch {
            try{
                coffeeUseCases.getAllTimeTypeStatsUseCase().collect{

                    state = state.copy(
                        allCoffeesStats = it,
                        coffeeCount = state.allCoffeesStats.size
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