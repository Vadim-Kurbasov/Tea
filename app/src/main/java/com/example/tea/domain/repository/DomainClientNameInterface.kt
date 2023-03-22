package com.example.tea.domain.repository

import com.example.tea.domain.models.ClientNameModelDomain

interface DomainClientNameInterface {
    fun saveClientNameMemory(clientNameDomainModel: ClientNameModelDomain)
    fun getClientNameMemory(): ClientNameModelDomain
}