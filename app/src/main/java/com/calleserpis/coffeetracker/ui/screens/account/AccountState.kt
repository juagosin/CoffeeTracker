package com.calleserpis.coffeetracker.ui.screens.account

data class AccountState(
    val isAnonymous: Boolean = true,
    val email: String? = null,
    val isLoading: Boolean = false,
    val showSuccess: Boolean = false,
    val errorMessage: String? = null
)
