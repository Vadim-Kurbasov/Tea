package com.example.tea.data.repository

import com.example.tea.data.storage.WhatsAppInterface
import com.example.tea.data.models.WhatsAppModel
import com.example.tea.domain.models.WhatsAppModelDomain
import com.example.tea.domain.repository.DomainWhatsAppInterface

class WhatsAppRepository(private val whatsAppInterface: WhatsAppInterface): DomainWhatsAppInterface {
    override fun sendMessage(whatsAppModelDomain: WhatsAppModelDomain) {

        val whatsAppModel = WhatsAppModel(strToSend = whatsAppModelDomain.strToSend)
        whatsAppInterface.sendWhatsApp(whatsAppModel)
    }
}