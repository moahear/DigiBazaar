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
import java.text.SimpleDateFormat
import java.util.Locale

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

class DataStoreImpl(private val context: Context) : IDataStoreRepository {
    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_ADDRESS = "key_address"
        private const val KEY_POSTAL_CODE = "key_postal_code"
        private const val KEY_LOGIN_TIME = "key_login_time"
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

    override suspend fun saveAddress(value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(KEY_ADDRESS)] = value
        }
    }

    override suspend fun getAddress(): String {
        return try {
            context.dataStore.data.first()[stringPreferencesKey(KEY_ADDRESS)]
                ?: "Click to add address"
        } catch (exception: Exception) {
            exception.printStackTrace()
            ""
        }
    }

    override suspend fun saveLoginTime() {
        context.dataStore.edit {
            it[stringPreferencesKey(KEY_LOGIN_TIME)] = SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss z",
                Locale.getDefault()
            ).format(System.currentTimeMillis())
        }
    }

    override suspend fun getLoginTime(): String {
        return try {
            context.dataStore.data.first()[stringPreferencesKey(KEY_LOGIN_TIME)] ?: ""
        } catch (exception: Exception) {
            exception.printStackTrace()
            ""
        }
    }

    override suspend fun savePostalCode(value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(KEY_POSTAL_CODE)] = value
        }
    }

    override suspend fun getPostalCode(): String {
        return try {
            context.dataStore.data.first()[stringPreferencesKey(KEY_POSTAL_CODE)]
                ?: "Click to add postal code"
        } catch (exception: Exception) {
            exception.printStackTrace()
            ""
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