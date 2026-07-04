package com.calleserpis.coffeetracker.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.content.Intent
import com.calleserpis.coffeetracker.data.firebase.FirebaseAuthManager
import com.calleserpis.coffeetracker.domain.use_case.SignInWithGoogleUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {

    fun getGoogleSignInIntent(): Intent = googleSignInClient.signInIntent

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state.asStateFlow()

    init {
        refreshAccountState()
    }

    fun refreshAccountState() {
        _state.update {
            it.copy(
                isAnonymous = authManager.isAnonymous,
                email = authManager.linkedEmail
            )
        }
    }

    fun onGoogleIdTokenReceived(idToken: String?) {
        if (idToken.isNullOrBlank()) {
            _state.update { it.copy(errorMessage = "No se pudo obtener el token de Google") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, showSuccess = false) }
            try {
                signInWithGoogleUseCase(idToken)
                refreshAccountState()
                _state.update {
                    it.copy(
                        isLoading = false,
                        showSuccess = true
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al vincular cuenta"
                    )
                }
            }
        }
    }

    fun onGoogleSignInFailed(message: String?) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message ?: "Inicio de sesión cancelado"
            )
        }
    }
}
