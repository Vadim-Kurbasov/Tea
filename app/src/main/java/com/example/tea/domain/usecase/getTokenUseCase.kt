package com.example.tea.domain.usecase

import com.example.tea.domain.repository.DomainRepositoryInterface

class getTokenUseCase(private val domainRepositoryInterface: DomainRepositoryInterface) {

    fun execute() {
        domainRepositoryInterface.getTokenFromGoogleSite()
    }
}
