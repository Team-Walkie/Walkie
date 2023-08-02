package com.whyranoid.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "account")

class AccountDataStore(private val context: Context) {
    private val authIdKey = stringPreferencesKey("authId")
    val authId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[authIdKey]
        }

    private val userNameKey = stringPreferencesKey("userName")
    val userName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[userNameKey] ?: "None"
        }

    private val nickNameKey = stringPreferencesKey("nickName")
    val nickName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[nickNameKey] ?: "None"
        }

    private val profileUrlKey = stringPreferencesKey("profileUrl")
    val profileUrl: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[profileUrlKey] ?: "None"
        }
    suspend fun updateAuthId(uid: String) {
        context.dataStore.edit { preferences ->
            preferences[authIdKey] = uid
        }
    }

    suspend fun updateUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[userNameKey] = name
        }
    }

    suspend fun updateNickName(nickName: String) {
        context.dataStore.edit { preferences ->
            preferences[nickNameKey] = nickName
        }
    }

    suspend fun updateProfileUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[profileUrlKey] = url
        }
    }
}
