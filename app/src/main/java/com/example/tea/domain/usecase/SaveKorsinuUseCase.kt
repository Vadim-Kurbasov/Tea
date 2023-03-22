package com.example.tea.domain.usecase

import com.example.tea.domain.models.KosinaDomainModel
import com.example.tea.domain.repository.DomainKorsinaInterface

class SaveKorsinuUseCase(private val domainKorsinaInterface: DomainKorsinaInterface) {

    fun execute(kosinaDomainModel: KosinaDomainModel){
        domainKorsinaInterface.saveKorsinuToMemory(kosinaDomainModel)
    }
}