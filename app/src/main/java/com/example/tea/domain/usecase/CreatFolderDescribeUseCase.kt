package com.example.tea.domain.usecase

import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.repository.DomainYaDiskInterface

class CreatFolderDescribeUseCase(private val domainYadiskInterface: DomainYaDiskInterface) {

    fun execute(creatFolderModelDomain: CreatFolderModelDomain){
       domainYadiskInterface.creatFolderDescribe(creatFolderModelDomain)
    }
}