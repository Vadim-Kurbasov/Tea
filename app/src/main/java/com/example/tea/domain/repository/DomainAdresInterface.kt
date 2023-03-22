package com.example.tea.domain.repository

import com.example.tea.domain.models.AdresModelDomain

interface DomainAdresInterface {
    fun saveAdresMemory(adresDomainModel: AdresModelDomain)
    fun getAdresMemory(): AdresModelDomain
}