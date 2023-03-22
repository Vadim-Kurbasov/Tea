package com.example.tea.data.repository

import com.example.tea.data.models.AdresModel
import com.example.tea.data.storage.AdresInterface
import com.example.tea.domain.models.AdresModelDomain
import com.example.tea.domain.repository.DomainAdresInterface


class AdresRepository (private val adresInterface: AdresInterface):
    DomainAdresInterface {
    override fun saveAdresMemory(adresModelDomain: AdresModelDomain) {
        val adresModel = AdresModel(text = adresModelDomain.text)
        adresInterface.saveAdres(adresModel)
    }

    override fun getAdresMemory(): AdresModelDomain {
        val adresModel = adresInterface.getAdres()
        val adresModelDomain = AdresModelDomain(text = adresModel.text)
        return adresModelDomain
    }
}