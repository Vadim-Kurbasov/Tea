package com.example.tea.data.storage.GoogleSiteToken

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GoogleSiteTokenViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoogleSiteTokenViewModel() as T
    }
}