package com.calleserpis.coffeetracker.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth
) {
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val currentUid: String?
        get() = auth.currentUser?.uid

    val isAnonymous: Boolean
        get() = auth.currentUser?.isAnonymous != false

    val linkedEmail: String?
        get() = auth.currentUser?.takeIf { !it.isAnonymous }?.email

    val isGoogleLinked: Boolean
        get() = auth.currentUser?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } == true

    suspend fun ensureSignedIn(): String {
        auth.currentUser?.uid?.let { return it }
        return auth.signInAnonymously().await().user?.uid
            ?: error("No se pudo iniciar sesión anónima en Firebase")
    }

    /**
     * En el mismo móvil: vincula Google a la cuenta anónima (mismo uid, mismos datos en la nube).
     * En otro móvil: inicia sesión con Google y recupera el uid vinculado.
     */
    suspend fun signInOrLinkWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val user = auth.currentUser

        when {
            user != null && user.isAnonymous -> {
                try {
                    user.linkWithCredential(credential).await()
                } catch (_: FirebaseAuthUserCollisionException) {
                    auth.signInWithCredential(credential).await()
                }
            }
            user == null -> {
                auth.signInWithCredential(credential).await()
            }
            else -> {
                if (!isGoogleLinked) {
                    try {
                        user.linkWithCredential(credential).await()
                    } catch (_: FirebaseAuthUserCollisionException) {
                        auth.signInWithCredential(credential).await()
                    }
                }
            }
        }
    }
}
