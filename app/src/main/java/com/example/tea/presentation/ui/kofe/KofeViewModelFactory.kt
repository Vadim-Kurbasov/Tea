package com.example.tea.presentation.ui.kofe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tea.data.repository.GetProductRepository
import com.example.tea.data.repository.KorsinaRepository
import com.example.tea.data.storage.korsina.KorsinaImp
import com.example.tea.data.storage.listfoldersname.GetListProductToDisplayImp
import com.example.tea.data.storage.listfoldersname.Object
import com.example.tea.domain.usecase.GetListProductUseCaseUseCase
import com.example.tea.domain.usecase.WorkWithKorzinaUseCase
import com.example.tea.presentation.home.KofeViewModel
import com.example.tea.presentation.ui.MainActivity.TokenViewModel

class KofeViewModelFactory(val token: String, val tokenViewModel: TokenViewModel, val context: Context) : ViewModelProvider.Factory {

    val domainGetProductInterface by lazy(LazyThreadSafetyMode.NONE){
        GetProductRepository(GetListProductToDisplayImp())
    }
    private val getListProductUseCaseUseCase by lazy(LazyThreadSafetyMode.NONE){ GetListProductUseCaseUseCase(domainGetProductInterface) }

    val domainKorsinaInterface by lazy(LazyThreadSafetyMode.NONE) { KorsinaRepository(
        KorsinaImp(context)
    ) }
    private val workWithKorzinaUseCase by lazy(LazyThreadSafetyMode.NONE) { WorkWithKorzinaUseCase(domainKorsinaInterface) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val kofeViewModel = KofeViewModel(
            token = token,
            getListProductUseCaseUseCase = getListProductUseCaseUseCase,
            workWithKorzinaUseCase =workWithKorzinaUseCase,
            tokenViewModel = tokenViewModel )
        Object.kofeVM = kofeViewModel
        return kofeViewModel as T
    }
}