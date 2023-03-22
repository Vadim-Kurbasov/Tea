package com.example.tea.data.storage.adres

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tea.data.models.AdresModel
import com.example.tea.data.models.ClientNameModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map



val Context.settingAdresStoreManager: DataStore<Preferences> by preferencesDataStore(name = "adres")
object AdresStoreManager {
    suspend fun saveValue(context: Context, key: String, adresModel: AdresModel) {
        val wrappedKey = stringPreferencesKey(key)
        context.settingAdresStoreManager.edit {
            it[wrappedKey] = adresModel.text.toString()
        }
    }

    suspend fun getValue(context: Context, key: String, default: String = ""):String {
        val wrappedKey = stringPreferencesKey(key)
        val valueFlow = context.settingAdresStoreManager.data.map {
            it[wrappedKey] ?:default
        }
        return valueFlow.first()
    }
}