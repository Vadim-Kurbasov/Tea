package com.example.tea.domain.usecase

import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.repository.DomainVadimInterface

class CreatVadimFolderUseCase (private val domainVadimInterface: DomainVadimInterface) {
    fun execute(creatFolderModelDomain: CreatFolderModelDomain){
        domainVadimInterface.creatFolderVadim(creatFolderModelDomain)
    }
}