package com.gamil.moahear.digibazaar.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gamil.moahear.digibazaar.data.repository.TokenInMemory
import com.gamil.moahear.digibazaar.utils.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

class DataStoreImpl(private val context: Context) : IDataStoreRepository {
    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val KEY_USER_NAME = "key_user_name"
    }

    override suspend fun loadToken() {
        runBlocking {
            TokenInMemory.refreshToken(getUserName(), getToken())
        }
    }

    override suspend fun saveToken(value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(KEY_TOKEN)] = value
        }
    }

    override suspend fun getToken(): String? {
        return try {
            context.dataStore.data.first()[stringPreferencesKey(KEY_TOKEN)]
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    override suspend fun saveUsername(value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(KEY_USER_NAME)] = value
        }
    }

    override suspend fun getUserName(): String? {
        return try {
            context.dataStore.data.first()[stringPreferencesKey(KEY_USER_NAME)]
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    override suspend fun clearDataStore() {
        context.dataStore.edit { it.clear() }
    }
}