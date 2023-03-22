package com.example.tea.domain.repository

import com.example.tea.domain.models.GreetingTextDomainModel

interface DomainGreetingInterface {
    fun saveGreetingMemory(greetingTextDomainModel: GreetingTextDomainModel)
    fun getGreetingToMemory(): GreetingTextDomainModel
}