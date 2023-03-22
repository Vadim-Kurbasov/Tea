package com.example.tea.domain.usecase

import com.example.tea.domain.models.ClientNameModelDomain
import com.example.tea.domain.repository.DomainClientNameInterface

class GetClientNameUseCase (private val domainClientNameInterface: DomainClientNameInterface) {

    fun execute(): ClientNameModelDomain {
        val clientNameModelDomain = domainClientNameInterface.getClientNameMemory()
        return clientNameModelDomain
    }
}