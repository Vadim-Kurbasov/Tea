package com.example.tea.data.storage.creatfolder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatFolderYaDiskViewModel: ViewModel() {

    val stringVm : MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    init {
        stringVm.value = ""
    }

    fun setViewModel(str: String){
        stringVm.value = str
    }
}

