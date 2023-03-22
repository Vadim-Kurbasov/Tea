package com.example.tea.data.storage

import com.example.tea.data.models.AdresModel

interface AdresInterface {
    fun  saveAdres(adresModel: AdresModel)
    fun getAdres(): AdresModel
}