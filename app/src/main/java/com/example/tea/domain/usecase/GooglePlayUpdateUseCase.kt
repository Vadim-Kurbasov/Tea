package com.example.tea.domain.usecase

import com.example.tea.domain.repository.DomainGooglePlayUpdateInterface

class GooglePlayUpdateUseCase(private val domainGooglePlayUpdateInterface: DomainGooglePlayUpdateInterface) {

    fun execute(){
        domainGooglePlayUpdateInterface.updateApp()
    }
}