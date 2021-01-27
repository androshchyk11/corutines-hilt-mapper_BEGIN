package ua.oleksii.fitplantest.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val context: Context) {

    // to have preferenceKeys
    object PreferenceKeys {
        val TOKEN = stringPreferencesKey("TOKEN")
    }

    companion object {
        const val PREFERENCE_NAME = "my_preference"
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )


//    val readDataFromDataStore: Flow<String> = dataStore.data
//        .catch { exception ->
//            emit(emptyPreferences())
//        }
//        .map { preference ->
//            val value = preference[PreferenceKeys.TOKEN] ?: "empty"
//            value
//        } todo figure out this part

    suspend fun saveAccessToken( token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.TOKEN] = token
        }
    }

    suspend fun getAccessToken(key: Preferences.Key<String>): String {
        val preferences = dataStore.data.first()
        return preferences[PreferenceKeys.TOKEN] ?: ""
    }
}

