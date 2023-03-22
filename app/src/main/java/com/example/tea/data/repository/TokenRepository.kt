package com.example.tea.data.repository

import com.example.tea.data.storage.GoogleSiteTokenInterface
import com.example.tea.domain.repository.DomainRepositoryInterface

class TokenRepository(private val googleSiteTokenInterface: GoogleSiteTokenInterface): DomainRepositoryInterface {

    override fun getTokenFromGoogleSite(){
        googleSiteTokenInterface.getTokenFromGoogleSite()
  }
}


