package com.example.tea.data.repository

import com.example.tea.data.storage.DownloadLinkInterface
import com.example.tea.data.models.LinkToDownloadImageModel
import com.example.tea.domain.models.LinkToDownloadImageModelDomain
import com.example.tea.domain.repository.DomainDownLoadLinkInterface

class DownloadLinkRepository (private val downloadLinkInterface: DownloadLinkInterface):
    DomainDownLoadLinkInterface {

    override fun downloadLink(linkToDownloadImageModelDomain: LinkToDownloadImageModelDomain) {
        val linkToDownloadImageModel = LinkToDownloadImageModel(token = linkToDownloadImageModelDomain.token, choisenFolder = linkToDownloadImageModelDomain.choisenFolder, nameFolder = linkToDownloadImageModelDomain.nameFolder)
        downloadLinkInterface.downLoadImage(linkToDownloadImageModel)
    }
}