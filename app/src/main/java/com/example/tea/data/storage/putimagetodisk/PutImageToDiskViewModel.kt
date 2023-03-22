package com.example.tea.data.storage.putimagetodisk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PutImageToDiskViewModel: ViewModel() {

    val booleanVm : MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }

    init {
        booleanVm.value = false
    }

    fun setViewModel(boolean: Boolean){
        booleanVm.value = boolean
    }
}