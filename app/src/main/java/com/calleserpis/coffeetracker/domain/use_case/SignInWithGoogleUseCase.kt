package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.data.firebase.FirebaseAuthManager
import com.calleserpis.coffeetracker.data.sync.CoffeeSyncManager
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val syncManager: CoffeeSyncManager
) {
    suspend operator fun invoke(idToken: String) {
        authManager.signInOrLinkWithGoogle(idToken)
        syncManager.syncAll()
    }
}
