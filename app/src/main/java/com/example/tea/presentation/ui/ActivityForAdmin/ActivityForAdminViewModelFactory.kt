package com.example.tea.presentation.ui.ActivityForAdmin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tea.data.repository.TokenRepository
import com.example.tea.data.repository.YaDiskRepository
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenImp
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenViewModel
import com.example.tea.data.storage.creatfolder.CreatFolderYaDiskImp
import com.example.tea.data.storage.creatfolder.CreatFolderYaDiskViewModel
import com.example.tea.data.storage.linktouploadimage.LinkToUploadImageImp
import com.example.tea.data.storage.putimagetodisk.PutImageToDiskImp
import com.example.tea.data.storage.putimagetodisk.PutImageToDiskViewModel
import com.example.tea.domain.usecase.*

class ActivityForAdminViewModelFactory(
    context: Context,
    val creatFolderYaDiskViewModel: CreatFolderYaDiskViewModel,
    val googleSiteTokenViewModel:GoogleSiteTokenViewModel,
    val putImageToDiskViewModel: PutImageToDiskViewModel): ViewModelProvider.Factory {

    val contentResolver = context.getContentResolver()

    val domainRepositopyInterface by lazy(LazyThreadSafetyMode.NONE) { TokenRepository(GoogleSiteTokenImp(googleSiteTokenViewModel))}
    private val getTokenUseCase by lazy(LazyThreadSafetyMode.NONE) { getTokenUseCase(domainRepositopyInterface) }

    val domaimYaDiskInterface by lazy(LazyThreadSafetyMode.NONE){
        YaDiskRepository(
            CreatFolderYaDiskImp(creatFolderYaDiskViewModel),
            LinkToUploadImageImp(),
            PutImageToDiskImp(putImageToDiskViewModel),
        )
    }
    private val creatFolderProductUseCase by lazy(LazyThreadSafetyMode.NONE){ CreatFolderProductUseCase(domaimYaDiskInterface) }
    private val creatFolderChosenUseCase by lazy(LazyThreadSafetyMode.NONE){ CreatFolderChosenUseCase(domaimYaDiskInterface) }
    private val creatFolderNameUseCase by lazy(LazyThreadSafetyMode.NONE){ CreatFolderNameUseCase(domaimYaDiskInterface) }
    private val creatFolderDescribeUseCase by lazy(LazyThreadSafetyMode.NONE){ CreatFolderDescribeUseCase(domaimYaDiskInterface) }
    private val creatFolderPriseUseCase by lazy(LazyThreadSafetyMode.NONE){ CreatFolderPriseUseCase(domaimYaDiskInterface) }
    private val getLinkToUploadImageUseCase by lazy(LazyThreadSafetyMode.NONE){ GetLinkToUploadImageUseCase(domaimYaDiskInterface) }
    private val putImageToDiskUseCase by lazy(LazyThreadSafetyMode.NONE){ PutImageToDiskUseCase(domaimYaDiskInterface) }
    private val showTostUseCase by lazy(LazyThreadSafetyMode.NONE){ShowTostUseCase(context)}

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return ActivityForAdminViewModel(contentResolver,
            getTokenUseCase= getTokenUseCase,
            creatFolderProductUseCase= creatFolderProductUseCase,
            creatFolderChosentUseCase = creatFolderChosenUseCase,
            creatFolderNameUseCase = creatFolderNameUseCase,
            creatFolderDescribeUseCase = creatFolderDescribeUseCase,
            creatFolderPriseUseCase = creatFolderPriseUseCase,
            getLinkToUploadImageUseCase = getLinkToUploadImageUseCase,
            putImageToDiskUseCase = putImageToDiskUseCase,
            showTostUseCase = showTostUseCase,
            ) as T
    }
}