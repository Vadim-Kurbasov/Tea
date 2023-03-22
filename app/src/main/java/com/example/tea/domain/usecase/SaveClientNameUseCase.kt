package com.example.tea.domain.usecase

import com.example.tea.domain.models.ClientNameModelDomain
import com.example.tea.domain.repository.DomainClientNameInterface

class SaveClientNameUseCase (private val domainClientNameInterface: DomainClientNameInterface) {
    fun execute(clientNameModelDomain: ClientNameModelDomain){
        domainClientNameInterface.saveClientNameMemory(clientNameModelDomain)
    }
}