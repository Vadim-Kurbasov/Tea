package com.example.tea.domain.repository

import com.example.tea.data.models.HrefModel
import com.example.tea.domain.models.*

interface DomainYaDiskInterface {

    fun creatFolderProdut( creatFolderModelDomain: CreatFolderModelDomain)
    fun creatFolderChosen( creatFolderModelDomain: CreatFolderModelDomain)
    fun creatFolderName( creatFolderModelDomain: CreatFolderModelDomain)
    fun creatFolderDescribe( creatFolderModelDomain: CreatFolderModelDomain)
    fun creatFolderPrise( creatFolderModelDomain: CreatFolderModelDomain)
    fun getLinkToUploadImage(linkToUploadImageModelDomain: LinkToUploadImageModelDomain): HrefModel
    fun putImageToDisk(putImageToDiskModelDomain: PutImageToDiskModelDomain)
}