package az.autumn.myhome.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(context: Context) {
    private val dataStore = context.dataStore

    val backendUrlFlow: Flow<String> = dataStore.data
        .map { prefs -> prefs[PreferencesKeys.BACKEND_URL] ?: "http://192.168.0.239:8989" }

    suspend fun setBackendUrl(url: String) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.BACKEND_URL] = url
        }
    }

    private object PreferencesKeys {
        val BACKEND_URL = stringPreferencesKey("backend_url")
    }
}