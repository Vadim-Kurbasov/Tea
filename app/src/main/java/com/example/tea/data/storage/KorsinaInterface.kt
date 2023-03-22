package com.example.tea.data.storage

import com.example.tea.data.models.KorsinaModel
import com.example.tea.data.models.StrFromMemoryModel

interface KorsinaInterface {
    fun  saveKorsina(korsinaModel: KorsinaModel)
    fun getKorsina(): StrFromMemoryModel
}