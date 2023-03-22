package com.example.tea.domain.usecase

import com.example.tea.domain.models.GreetingTextDomainModel
import com.example.tea.domain.repository.DomainGreetingInterface

class SaveGreetingTextUseCase (private val domainGreetingInterface: DomainGreetingInterface) {

    fun execute(greetingTextDomainModel: GreetingTextDomainModel){
        domainGreetingInterface.saveGreetingMemory(greetingTextDomainModel)
    }
}