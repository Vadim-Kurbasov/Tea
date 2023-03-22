package com.example.tea.presentation.ui.showproductdialog

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tea.data.repository.DownloadLinkRepository
import com.example.tea.data.repository.KorsinaRepository
import com.example.tea.data.storage.korsina.KorsinaImp
import com.example.tea.data.storage.linktodownloadimage.LinkDowloadObject
import com.example.tea.data.storage.linktodownloadimage.LinkToDownloadImageImp
import com.example.tea.domain.usecase.DownloadLinkUseCase
import com.example.tea.domain.usecase.GetKorsinaUseCase
import com.example.tea.domain.usecase.SaveKorsinuUseCase
import com.example.tea.domain.usecase.ShowTostUseCase

class ShowProductViewModelFactory(
    val token: String,
    val choisenFolder: String,
    val nameFolder: String,
    val cena: Int,
    context: Context
): ViewModelProvider.Factory  {


    val domainDownLoadLinkInterface by lazy(LazyThreadSafetyMode.NONE){
        DownloadLinkRepository(LinkToDownloadImageImp())
    }
    private val downloadLinkUseCase by lazy(LazyThreadSafetyMode.NONE){ DownloadLinkUseCase(domainDownLoadLinkInterface) }


    val domainKorsinaInterface by lazy(LazyThreadSafetyMode.NONE) { KorsinaRepository(
        KorsinaImp(context)
    ) }
    private val getKorsinuUseCase by lazy(LazyThreadSafetyMode.NONE) { GetKorsinaUseCase(domainKorsinaInterface) }
    private val saveKorsinuUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveKorsinuUseCase(domainKorsinaInterface) }

    private val showTostUseCase by lazy(LazyThreadSafetyMode.NONE){ ShowTostUseCase(context) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val showProductViewModel = ShowProductViewModel(token = token,
            downloadLinkUseCase = downloadLinkUseCase,
            choisenFolder = choisenFolder,
            nameFolder = nameFolder,
            cena = cena,
            getKorsinuUseCase,
            saveKorsinuUseCase,
            showTostUseCase
        )
        LinkDowloadObject.showProductViewModel = showProductViewModel

        return showProductViewModel as T
    }
}