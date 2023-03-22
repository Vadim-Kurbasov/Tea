package com.example.tea.domain.usecase

import com.example.tea.domain.models.WhatsAppModelDomain
import com.example.tea.domain.repository.DomainWhatsAppInterface

class WhatsAppUseCase(private val domainWhatsAppInterface: DomainWhatsAppInterface) {
    fun execute(whatsAppModelDomain: WhatsAppModelDomain){
        domainWhatsAppInterface.sendMessage(whatsAppModelDomain)
    }
}