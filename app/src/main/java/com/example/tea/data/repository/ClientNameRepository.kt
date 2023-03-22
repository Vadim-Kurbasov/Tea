package com.example.tea.data.repository

import com.example.tea.data.models.ClientNameModel
import com.example.tea.data.storage.ClientNameInterface
import com.example.tea.domain.models.ClientNameModelDomain
import com.example.tea.domain.repository.DomainClientNameInterface


class ClientNameRepository (private val clientNameInterface: ClientNameInterface):
    DomainClientNameInterface {
    override fun saveClientNameMemory(clientNameModelDomain: ClientNameModelDomain) {
        val clientNameModel = ClientNameModel(text = clientNameModelDomain.text)
        clientNameInterface.saveClientName(clientNameModel)
    }

    override fun getClientNameMemory(): ClientNameModelDomain {
        val clientNameModel = clientNameInterface.getClientName()
        val clientNameModelDomain = ClientNameModelDomain(text = clientNameModel.text)
        return clientNameModelDomain
    }
}