package com.example.tea.domain.usecase

import com.example.tea.domain.models.LinkToDownloadImageModelDomain
import com.example.tea.domain.repository.DomainDownLoadLinkInterface

class DownloadLinkUseCase(private val domainDownLoadLinkInterface: DomainDownLoadLinkInterface) {

    fun execute(linkToDownloadImageModelDomain: LinkToDownloadImageModelDomain){
        domainDownLoadLinkInterface.downloadLink(linkToDownloadImageModelDomain)
    }
}