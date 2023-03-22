package com.example.tea.presentation.ui.showproductdialog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.example.tea.domain.models.KosinaDomainModel
import com.example.tea.domain.models.LinkToDownloadImageModelDomain
import com.example.tea.domain.usecase.DownloadLinkUseCase
import com.example.tea.domain.usecase.GetKorsinaUseCase
import com.example.tea.domain.usecase.SaveKorsinuUseCase
import com.example.tea.domain.usecase.ShowTostUseCase
import com.example.tea.presentation.ui.Adapters.AdapterImageProduct

class ShowProductViewModel(
    token: String,
    downloadLinkUseCase: DownloadLinkUseCase,
    val choisenFolder: String,
    nameFolder: String,
    var cena: Int,
    val getKorsinuUseCase: GetKorsinaUseCase,
    val saveKorsinuUseCase: SaveKorsinuUseCase,
    val showTostUseCase: ShowTostUseCase
    ): ViewModel() {

    lateinit var pager1: ViewPager

    lateinit var itemKorsina: ItemKorsina
    var finishKorsinsList = ArrayList<ItemKorsina>()

    val hrefImageList = ArrayList<String>()

    var nameFolderLiveData = MutableLiveData<String>()
    var pager1LiveData = MutableLiveData<ViewPager>()
    var describeLiveData = MutableLiveData<String>()
    var summaLiveData = MutableLiveData<Int>()
    var countLiveData = MutableLiveData<Int>()

    lateinit private var adapterImageProduct: AdapterImageProduct

    init{
        val linkToDownloadImageModelDomain = LinkToDownloadImageModelDomain(token, choisenFolder,nameFolder)
        downloadLinkUseCase.execute(linkToDownloadImageModelDomain)

        if( choisenFolder == "Чай"|| choisenFolder == "Кофе" ){
            val coeffSum = cena * 0.5f
            summaLiveData.value = coeffSum.toInt()
            countLiveData.value = 50
        }
        else{
            summaLiveData.value = cena
            countLiveData.value = 1
        }
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

    fun setCount(plusOrMinus: String){
        if(plusOrMinus == "plus"){
            if(choisenFolder == "Чай"|| choisenFolder == "Кофе"){
                val coeff = cena / 2
                summaLiveData.value = summaLiveData.value?.plus(coeff)
                countLiveData.value = countLiveData.value?.plus(50)
            }
            else {
                countLiveData.value = countLiveData.value?.plus(1)
                summaLiveData.value = summaLiveData.value?.plus(cena)
            }
        }
        if(plusOrMinus == "minus" && countLiveData.value!! >1){
            if(choisenFolder == "Чай"|| choisenFolder == "Кофе"){
                val coeff = cena / 2
                if(countLiveData.value!! >50) {
                    summaLiveData.value = summaLiveData.value?.minus(coeff)
                    countLiveData.value = countLiveData.value?.minus(50)
                }
            }
            else {
                countLiveData.value = countLiveData.value?.minus(1)
                summaLiveData.value = summaLiveData.value?.minus(cena)
            }
        }
    }

    fun putItemVkorsinu(){
        itemKorsina = nameFolderLiveData.value?.let { countLiveData.value?.let { it1 -> summaLiveData.value?.let { it2 -> ItemKorsina(name = it, count = it1, cena = cena, itog = it2, number = choisenFolder, n="") } } }!!
        var kosinaDomainModel: KosinaDomainModel
        kosinaDomainModel = getKorsinuUseCase.execute()
        finishKorsinsList = kosinaDomainModel.korsinaList
        finishKorsinsList.add(itemKorsina)
        kosinaDomainModel.korsinaList = finishKorsinsList
        saveKorsinuUseCase.execute(kosinaDomainModel)
        showTostUseCase.execute("Добавлено")

    }
}

