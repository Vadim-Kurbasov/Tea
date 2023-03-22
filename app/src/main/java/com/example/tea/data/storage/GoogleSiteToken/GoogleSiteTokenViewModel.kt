package com.example.tea.data.storage.GoogleSiteToken

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GoogleSiteTokenViewModel : ViewModel(){

    val tokenLifeData : MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    init{
        tokenLifeData.value = ""
    }

    fun setTokenLifeData(token:String){
        tokenLifeData.value = token
    }
}