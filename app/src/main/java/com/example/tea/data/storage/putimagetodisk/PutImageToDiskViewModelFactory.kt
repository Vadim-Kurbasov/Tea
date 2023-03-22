package com.example.tea.data.storage.putimagetodisk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PutImageToDiskViewModelFactory: ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PutImageToDiskViewModel() as T
    }
}