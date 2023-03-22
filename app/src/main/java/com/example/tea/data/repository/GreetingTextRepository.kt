package com.example.tea.data.repository

import com.example.tea.data.models.GreetingTextModel
import com.example.tea.data.storage.GreetingTextInterface
import com.example.tea.domain.models.GreetingTextDomainModel
import com.example.tea.domain.repository.DomainGreetingInterface

class GreetingTextRepository(private val greetingTextInterface: GreetingTextInterface):
    DomainGreetingInterface {

    override fun saveGreetingMemory(greetingTextDomainModel: GreetingTextDomainModel) {
        val greetingTextModel = GreetingTextModel(text = greetingTextDomainModel.text)
        greetingTextInterface.saveTextGreeting(greetingTextModel)
    }

    override fun getGreetingToMemory(): GreetingTextDomainModel {

        val greetingTextModel = greetingTextInterface.getTextGreeting()
        val greetingTextDomainModel = GreetingTextDomainModel(text = greetingTextModel.text)
        return greetingTextDomainModel
    }
}