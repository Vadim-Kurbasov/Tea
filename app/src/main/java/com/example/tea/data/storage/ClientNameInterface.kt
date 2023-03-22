package com.example.tea.data.storage

import com.example.tea.data.models.ClientNameModel

interface ClientNameInterface {
    fun  saveClientName(clientNameModel: ClientNameModel)
    fun getClientName(): ClientNameModel
}