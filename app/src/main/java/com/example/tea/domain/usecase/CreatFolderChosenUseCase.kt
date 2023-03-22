package com.example.tea.domain.usecase

import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.repository.DomainYaDiskInterface

class CreatFolderChosenUseCase (private val domainYadiskInterface: DomainYaDiskInterface) {

    fun execute(creatFolderModelDomain: CreatFolderModelDomain){
      domainYadiskInterface.creatFolderChosen(creatFolderModelDomain)
    }
}