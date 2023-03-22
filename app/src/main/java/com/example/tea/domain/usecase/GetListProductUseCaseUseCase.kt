package com.example.tea.domain.usecase

import com.example.tea.domain.models.GetListProductToDisplayModelDomain
import com.example.tea.domain.repository.DomainGetProductInterface

class GetListProductUseCaseUseCase(private val domainGetProductInterface: DomainGetProductInterface) {

    fun execute(getListProductToDisplayModelDomain:GetListProductToDisplayModelDomain){
        domainGetProductInterface.getProductToDisplay(getListProductToDisplayModelDomain)
    }
}