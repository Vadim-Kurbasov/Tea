package com.example.tea.data.storage.clientname
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tea.data.models.ClientNameModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.settingClientNameStoreManager: DataStore<Preferences> by preferencesDataStore(name = "client")
object ClientNameStoreManager {
    suspend fun saveValue(context: Context, key: String, clientNameModel: ClientNameModel) {
        val wrappedKey = stringPreferencesKey(key)
        context.settingClientNameStoreManager.edit {
            it[wrappedKey] = clientNameModel.text.toString()
        }
    }

    suspend fun getValue(context: Context, key: String, default: String = ""):String {
        val wrappedKey = stringPreferencesKey(key)
        val valueFlow = context.settingClientNameStoreManager.data.map {
            it[wrappedKey] ?:default
        }
        return valueFlow.first()
    }
}