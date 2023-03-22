package com.example.tea.data.storage

import com.example.tea.data.models.WhatsAppModel

interface WhatsAppInterface {
    fun sendWhatsApp(whatsAppModel: WhatsAppModel)
}