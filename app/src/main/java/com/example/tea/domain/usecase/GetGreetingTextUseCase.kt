package com.example.tea.domain.usecase

import com.example.tea.domain.models.GreetingTextDomainModel
import com.example.tea.domain.repository.DomainGreetingInterface

class GetGreetingTextUseCase (private val domainGreetingInterface: DomainGreetingInterface) {

    fun execute(): GreetingTextDomainModel {
        val greetingTextDomainModel = domainGreetingInterface.getGreetingToMemory()
        return greetingTextDomainModel
    }
}