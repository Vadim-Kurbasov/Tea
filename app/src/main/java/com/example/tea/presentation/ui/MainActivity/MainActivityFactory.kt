package com.example.tea.presentation.ui.MainActivity

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tea.data.repository.GooglePlayUpdateRepository
import com.example.tea.data.repository.TokenRepository
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenImp
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenViewModel
import com.example.tea.data.storage.googleplayupdate.GooglePlayUpdateImp
import com.example.tea.domain.usecase.GetGreetingTextUseCase
import com.example.tea.domain.usecase.GooglePlayUpdateUseCase
import com.example.tea.domain.usecase.getTokenUseCase
import com.google.android.play.core.appupdate.AppUpdateManager

class MainActivityFactory (val googleSiteTokenViewModel: GoogleSiteTokenViewModel,
                           val getGreetingTextUseCase: GetGreetingTextUseCase,
                           val activity: Activity,
                           val appUpdateManager: AppUpdateManager): ViewModelProvider.Factory {

    val domainRepositopyInterface by lazy(LazyThreadSafetyMode.NONE) { TokenRepository(GoogleSiteTokenImp(googleSiteTokenViewModel)) }
    private val getTokenUseCase by lazy(LazyThreadSafetyMode.NONE) { getTokenUseCase(domainRepositopyInterface) }

    val domainGooglePlayUpdateInterface by lazy(LazyThreadSafetyMode.NONE) { GooglePlayUpdateRepository(GooglePlayUpdateImp(activity = activity, appUpdateManager = appUpdateManager)) }
    private val googlePlayUpdateUseCase by lazy(LazyThreadSafetyMode.NONE) { GooglePlayUpdateUseCase(domainGooglePlayUpdateInterface) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            getTokenUseCase = getTokenUseCase,
            getGreetingTextUseCase = getGreetingTextUseCase,
            googlePlayUpdateUseCase = googlePlayUpdateUseCase) as T
    }
}