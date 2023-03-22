package com.example.tea.domain.usecase

import com.example.tea.domain.models.PutImageToDiskModelDomain
import com.example.tea.domain.repository.DomainYaDiskInterface

class PutImageToDiskUseCase(private val domainYadiskInterface: DomainYaDiskInterface) {

    fun execute(putImageToDiskModelDomain: PutImageToDiskModelDomain){
        domainYadiskInterface.putImageToDisk(putImageToDiskModelDomain)
    }
}