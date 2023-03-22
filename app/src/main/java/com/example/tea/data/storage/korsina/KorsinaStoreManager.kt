package com.example.tea.data.storage.korsina

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.collections.ArrayList as ArrayList1

val Context.settingKorsinaStoreManager: DataStore<Preferences> by preferencesDataStore(name = "whatewer")
object KorsinaStoreManager {
    suspend fun saveValue(context: Context, key: String, value: ArrayList1<ItemKorsina>) {
        val wrappedKey = stringPreferencesKey(key)
        context.settingKorsinaStoreManager.edit {
            it[wrappedKey] = value.toString()
        }
    }

    suspend fun getValue(context: Context, key: String, default: String = ""): String {
        val wrappedKey = stringPreferencesKey(key)
        val valueFlow = context.settingKorsinaStoreManager.data.map {
            it[wrappedKey] ?:default
        }
        return valueFlow.first()
    }
}
