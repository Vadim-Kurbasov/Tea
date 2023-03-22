package com.example.tea.domain.repository

import com.example.tea.domain.models.WhatsAppModelDomain

interface DomainWhatsAppInterface {
    fun sendMessage( whatsAppModelDomain: WhatsAppModelDomain)
}