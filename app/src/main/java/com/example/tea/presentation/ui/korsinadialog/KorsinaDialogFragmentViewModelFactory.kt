package com.example.tea.presentation.ui.korsinadialog

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tea.data.repository.*
import com.example.tea.data.storage.adres.AdresImp
import com.example.tea.data.storage.clientname.ClientNameImp
import com.example.tea.data.storage.korsina.KorsinaImp
import com.example.tea.data.storage.vadim.CreatFolderVadimImp
import com.example.tea.domain.usecase.*

class KorsinaDialogFragmentViewModelFactory(context: Context): ViewModelProvider.Factory {

    val domainKorsinaInterface by lazy(LazyThreadSafetyMode.NONE) { KorsinaRepository(
        KorsinaImp(context)
    ) }
    private val workWithKorzinaUseCase by lazy(LazyThreadSafetyMode.NONE) { WorkWithKorzinaUseCase(domainKorsinaInterface) }

    val domaimVadimInterface by lazy(LazyThreadSafetyMode.NONE){
        VadimRepository(CreatFolderVadimImp(),)
    }
    private val creatVadimFolderUseCase by lazy(LazyThreadSafetyMode.NONE){ CreatVadimFolderUseCase(domaimVadimInterface) }

    val domainClientNameInterface by lazy(LazyThreadSafetyMode.NONE) { ClientNameRepository(
        ClientNameImp(context)
    ) }
    private val getClientNameUseCase by lazy(LazyThreadSafetyMode.NONE) { GetClientNameUseCase(domainClientNameInterface) }
    private val saveClientNameUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveClientNameUseCase(domainClientNameInterface) }


    val domainAdresInterface by lazy(LazyThreadSafetyMode.NONE) { AdresRepository(
        AdresImp(context)
    ) }
    private val getAdresUseCase by lazy(LazyThreadSafetyMode.NONE) { GetAdresUseCase(domainAdresInterface) }
    private val saveAdresUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveAdresUseCase(domainAdresInterface) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val korsinaDialogFragmentViewModel = KorsinaDialogFragmentViewModel(
            creatVadimFolderUseCase,
            getClientNameUseCase,
            saveClientNameUseCase,
            getAdresUseCase,
            saveAdresUseCase,
            workWithKorzinaUseCase)
        return korsinaDialogFragmentViewModel as T
    }
}