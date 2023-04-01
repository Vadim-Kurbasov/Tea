package com.example.tea.presentation.ui.showproductdialog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.example.tea.domain.models.LinkToDownloadImageModelDomain
import com.example.tea.domain.usecase.*
import com.example.tea.presentation.ui.Adapters.AdapterImageProduct

class ShowProductViewModel(
    token: String,
    downloadLinkUseCase: DownloadLinkUseCase,
    val choisenFolder: String,
    nameFolder: String,
    var cena: Int,
    val workWithKorzinaUseCase: WorkWithKorzinaUseCase
    ): ViewModel() {

    lateinit var pager1: ViewPager

    val hrefImageList = ArrayList<String>()

    var nameFolderLiveData = MutableLiveData<String>()
    var pager1LiveData = MutableLiveData<ViewPager>()
    var describeLiveData = MutableLiveData<String>()
    var summaLiveData = MutableLiveData<Int>()

    lateinit private var adapterImageProduct: AdapterImageProduct

    init{
        val linkToDownloadImageModelDomain = LinkToDownloadImageModelDomain(token, choisenFolder,nameFolder)
        downloadLinkUseCase.execute(linkToDownloadImageModelDomain)

        summaLiveData.value = cena
    }

    fun initAdapter(pager: ViewPager, adapter: AdapterImageProduct, context1: Context?){
        pager1 = pager
        adapterImageProduct = adapter
        adapterImageProduct = context1.let { it?.let { it1 -> AdapterImageProduct(hrefImageList, it1) }!! }
    }

    fun setHrefList(href: String){
        hrefImageList.add(href)
        showImage()
    }

    fun showImage( ){
        pager1LiveData.value = pager1
        pager1LiveData.value?.adapter = adapterImageProduct
    }

    fun setNameFolder(nameFolder: String){
        nameFolderLiveData.value = nameFolder
    }

    fun setDescribeFolder(describeFolder: String){
        describeLiveData.value = describeFolder
    }
}


