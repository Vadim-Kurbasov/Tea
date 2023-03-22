package com.example.tea.data.storage.creatfolder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreatFolderYaDiskFactory: ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatFolderYaDiskViewModel() as T
    }
}