package com.example.tea.domain.repository

import com.example.tea.domain.models.LinkToDownloadImageModelDomain

interface DomainDownLoadLinkInterface {
    fun downloadLink(linkToDownloadImageModelDomain: LinkToDownloadImageModelDomain)
}