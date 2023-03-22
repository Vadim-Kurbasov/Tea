package com.example.tea.domain.usecase

import com.example.tea.domain.models.AdresModelDomain
import com.example.tea.domain.repository.DomainAdresInterface

class GetAdresUseCase (private val domainAdresInterface: DomainAdresInterface) {

    fun execute(): AdresModelDomain {
        val adresModelDomain = domainAdresInterface.getAdresMemory()
        return adresModelDomain
    }
}