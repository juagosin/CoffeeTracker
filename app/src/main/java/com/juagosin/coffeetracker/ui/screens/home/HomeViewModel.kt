package com.juagosin.coffeetracker.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.Insert
import com.juagosin.coffeetracker.domain.model.CoffeePhrases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(HomeState())
    init {
        loadPhrases()
    }

    private fun loadPhrases() {
        val randomPhrase = CoffeePhrases.phrases.random()
        state = state.copy(
            randomPhrase = randomPhrase
        )

    }
}