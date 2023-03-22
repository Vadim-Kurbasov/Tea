package com.example.chocolate.data.storage.greetingtext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tea.data.models.GreetingTextModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.settingGreetingStoreManager: DataStore<Preferences> by preferencesDataStore(name = "what")
object GreetingStoreManager {
    suspend fun saveValue(context: Context, key: String, greetingTextModel: GreetingTextModel) {
        val wrappedKey = stringPreferencesKey(key)
        context.settingGreetingStoreManager.edit {
            it[wrappedKey] = greetingTextModel.toString()
        }
    }

    suspend fun getValue(context: Context, key: String, default: String = ""):String {
        val wrappedKey = stringPreferencesKey(key)
        val valueFlow = context.settingGreetingStoreManager.data.map {
            it[wrappedKey] ?:default
        }
        return valueFlow.first()
    }
}
