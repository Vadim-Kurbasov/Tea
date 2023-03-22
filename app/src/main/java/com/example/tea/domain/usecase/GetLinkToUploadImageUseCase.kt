package com.example.tea.domain.usecase

import com.example.tea.domain.models.HrefModelDomain
import com.example.tea.domain.models.LinkToUploadImageModelDomain
import com.example.tea.domain.repository.DomainYaDiskInterface

class GetLinkToUploadImageUseCase(private val domainYadiskInterface: DomainYaDiskInterface) {

    fun execute(linkToUploadImageModelDomain: LinkToUploadImageModelDomain): HrefModelDomain{
        val hrefModel = domainYadiskInterface.getLinkToUploadImage(linkToUploadImageModelDomain)
        val hrefModelDomain = HrefModelDomain(href = hrefModel.href)
        return hrefModelDomain
    }
}