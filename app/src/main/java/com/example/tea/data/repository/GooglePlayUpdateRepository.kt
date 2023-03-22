package com.example.tea.data.repository

import com.example.tea.data.storage.GooglePlayUpdateInterface
import com.example.tea.domain.repository.DomainGooglePlayUpdateInterface

class GooglePlayUpdateRepository(private val googlePlayUpdateInterface: GooglePlayUpdateInterface):
    DomainGooglePlayUpdateInterface {
    override fun updateApp() {
        googlePlayUpdateInterface.checkUpdate()
    }
}