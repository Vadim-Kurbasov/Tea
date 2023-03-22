package com.example.tea.data.repository

import com.example.tea.data.storage.KorsinaInterface
import com.example.tea.data.models.KorsinaModel
import com.example.tea.domain.models.KosinaDomainModel
import com.example.tea.domain.models.StrFromMemoryModelDomain
import com.example.tea.domain.repository.DomainKorsinaInterface

class KorsinaRepository(private val korsinaInterface: KorsinaInterface):DomainKorsinaInterface {

    override fun saveKorsinuToMemory(korsinaDomainModel: KosinaDomainModel) {
        val korsinaModel = KorsinaModel(korsinaList = korsinaDomainModel.korsinaList)
        korsinaInterface.saveKorsina(korsinaModel)
    }

    override fun getKorsinuToMemory(): StrFromMemoryModelDomain {

        val strFromMemoryModel = korsinaInterface.getKorsina()
        val strFromMemoryModelDomain = StrFromMemoryModelDomain(str = strFromMemoryModel.str)
        return strFromMemoryModelDomain
    }
}