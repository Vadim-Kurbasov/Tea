package com.example.tea.presentation.ui.MainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tea.domain.models.GreetingTextDomainModel
import com.example.tea.domain.usecase.GetGreetingTextUseCase
import com.example.tea.domain.usecase.GooglePlayUpdateUseCase
import com.example.tea.domain.usecase.getTokenUseCase

class MainActivityViewModel (private val getTokenUseCase: getTokenUseCase,
                             private val getGreetingTextUseCase: GetGreetingTextUseCase,
                             private val googlePlayUpdateUseCase: GooglePlayUpdateUseCase): ViewModel(){

    var greetingTextDomainModelLiveData = MutableLiveData<GreetingTextDomainModel>()

    init{
        getTokenUseCase.execute()
        greetingTextDomainModelLiveData.value = getGreetingTextUseCase.execute()
        googlePlayUpdateUseCase.execute()
    }

    override fun onCleared() {
        super.onCleared()
    }
}



