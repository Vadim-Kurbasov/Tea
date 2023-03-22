package com.example.tea.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tea.data.repository.GetProductRepository
import com.example.tea.data.storage.listfoldersname.GetListProductToDisplayImp
import com.example.tea.data.storage.listfoldersname.Object
import com.example.tea.domain.usecase.GetListProductUseCaseUseCase
import com.example.tea.presentation.home.TeaViewModel

class TeaViewModelFactory(val token: String) : ViewModelProvider.Factory {

    val domainGetProductInterface by lazy(LazyThreadSafetyMode.NONE){
        GetProductRepository(GetListProductToDisplayImp())
    }
    private val getListProductUseCaseUseCase by lazy(LazyThreadSafetyMode.NONE){ GetListProductUseCaseUseCase(domainGetProductInterface) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val teaViewModel = TeaViewModel(token = token, getListProductUseCaseUseCase = getListProductUseCaseUseCase)
        Object.teaVM = teaViewModel
        return teaViewModel as T
    }
}