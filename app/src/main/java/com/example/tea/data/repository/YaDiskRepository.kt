package com.example.tea.data.repository

import com.example.tea.data.models.CreatFolderModel
import com.example.tea.data.models.HrefModel
import com.example.tea.data.models.LinkToUploadImageModel
import com.example.tea.data.models.PutImageToDiskModel
import com.example.tea.data.storage.CreatFolderYaDiskInterface
import com.example.tea.data.storage.LinkToUploadImageInterface
import com.example.tea.data.storage.PutImageToDiskInterface
import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.models.LinkToUploadImageModelDomain
import com.example.tea.domain.models.PutImageToDiskModelDomain
import com.example.tea.domain.repository.DomainYaDiskInterface

class YaDiskRepository(private val creatFolderYaDiskInterface: CreatFolderYaDiskInterface,
                       private val linkToUploadImageInterface: LinkToUploadImageInterface,
                       private val putImageToDiskInterface: PutImageToDiskInterface): DomainYaDiskInterface {

    override fun creatFolderProdut(creatFolderModelDomain: CreatFolderModelDomain){
        val creatFolderModel= CreatFolderModel(token = creatFolderModelDomain.token, path = creatFolderModelDomain.path)
       creatFolderYaDiskInterface.creatFolderYaDisk(creatFolderModel)
    }

    override fun creatFolderChosen(creatFolderModelDomain: CreatFolderModelDomain) {
        val creatFolderModel= CreatFolderModel(token = creatFolderModelDomain.token, path = creatFolderModelDomain.path)
       creatFolderYaDiskInterface.creatFolderYaDisk(creatFolderModel)
    }

    override fun creatFolderName(creatFolderModelDomain: CreatFolderModelDomain) {
        val creatFolderModel= CreatFolderModel(token = creatFolderModelDomain.token, path = creatFolderModelDomain.path)
       creatFolderYaDiskInterface.creatFolderYaDisk(creatFolderModel)
    }

    override fun creatFolderDescribe(creatFolderModelDomain: CreatFolderModelDomain){
        val creatFolderModel= CreatFolderModel(token = creatFolderModelDomain.token, path = creatFolderModelDomain.path)
       creatFolderYaDiskInterface.creatFolderYaDisk(creatFolderModel)
    }

    override fun creatFolderPrise(creatFolderModelDomain: CreatFolderModelDomain){
        val creatFolderModel= CreatFolderModel(token = creatFolderModelDomain.token, path = creatFolderModelDomain.path)
       creatFolderYaDiskInterface.creatFolderYaDisk(creatFolderModel)
    }

    override fun getLinkToUploadImage(linkToUploadImageModelDomain: LinkToUploadImageModelDomain): HrefModel {
        val linkToUploadImageModel = LinkToUploadImageModel(token = linkToUploadImageModelDomain.token, uri = linkToUploadImageModelDomain.uri, path = linkToUploadImageModelDomain.path)
        val hrefModel= linkToUploadImageInterface.getLinkTiUploadImage(linkToUploadImageModel)
        return hrefModel
    }

    override fun putImageToDisk(putImageToDiskModelDomain: PutImageToDiskModelDomain) {
        val putImageToDiskModel = PutImageToDiskModel(uri = putImageToDiskModelDomain.uri, href = putImageToDiskModelDomain.href, contentResolver = putImageToDiskModelDomain.contentResolver)
        putImageToDiskInterface.putImageToDisk(putImageToDiskModel)
    }
}