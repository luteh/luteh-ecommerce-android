package com.luteh.ecommerce.data.datasource.local

import androidx.datastore.core.DataStore
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.luteh.ecommerce.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSource @Inject constructor(private val dataStore: DataStore<UserPreferences>) {
    suspend fun setLoginSession(loggedIn: Boolean) {
        dataStore.updateData {
            it.toBuilder().setIsLoggedIn(loggedIn).build()
        }
    }

    suspend fun getLoginSession(): Boolean {
        return dataStore.data.firstOrNull()?.isLoggedIn ?: false
    }

    suspend fun saveAccount(account: GoogleSignInAccount?) {
        dataStore.updateData {
            it.toBuilder().setUsername(account?.displayName ?: "").build()
        }
    }

    suspend fun getAccount(): String {
        return dataStore.data.firstOrNull()?.username ?: ""
    }
}