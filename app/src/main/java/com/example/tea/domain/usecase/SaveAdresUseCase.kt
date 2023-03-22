package com.example.tea.domain.usecase

import com.example.tea.domain.models.AdresModelDomain
import com.example.tea.domain.repository.DomainAdresInterface

class SaveAdresUseCase (private val domainAdresInterface: DomainAdresInterface) {
    fun execute(adresModelDomain: AdresModelDomain){
        domainAdresInterface.saveAdresMemory(adresModelDomain)
    }
}