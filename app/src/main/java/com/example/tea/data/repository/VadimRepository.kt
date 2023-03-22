package com.example.tea.data.repository

import com.example.tea.data.storage.CreatFolderYaDiskInterface
import com.example.tea.data.models.CreatFolderModel
import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.repository.DomainVadimInterface

class VadimRepository(private val creatFolderYaDiskInterface: CreatFolderYaDiskInterface):
    DomainVadimInterface {
        override fun creatFolderVadim(creatFolderModelDomain: CreatFolderModelDomain){
        val creatFolderModel= CreatFolderModel(token = creatFolderModelDomain.token, path = creatFolderModelDomain.path)
        creatFolderYaDiskInterface.creatFolderYaDisk(creatFolderModel)
    }
}