package com.example.tea.domain.repository

import com.example.tea.domain.models.KosinaDomainModel
import com.example.tea.domain.models.StrFromMemoryModelDomain

interface DomainKorsinaInterface {

    fun saveKorsinuToMemory(korsinaDomainModel: KosinaDomainModel)
    fun getKorsinuToMemory(): StrFromMemoryModelDomain
}