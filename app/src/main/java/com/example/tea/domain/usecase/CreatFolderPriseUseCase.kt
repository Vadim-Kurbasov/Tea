package com.example.tea.domain.usecase

import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.repository.DomainYaDiskInterface

class CreatFolderPriseUseCase(private val  domainYaDiskInterface: DomainYaDiskInterface) {
    fun execute(creatFolderModelDomain: CreatFolderModelDomain){
      domainYaDiskInterface.creatFolderPrise(creatFolderModelDomain)
    }
}