package com.example.tea.presentation.ui.korsinadialog

import android.graphics.Typeface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.domain.models.*
import com.example.tea.domain.usecase.*
import com.example.tea.presentation.ui.Adapters.KorsinaAdapter
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina
import java.util.*

class KorsinaDialogFragmentViewModel(
   val creatVadimFolderUseCase: CreatVadimFolderUseCase,
       getClientNameUseCase: GetClientNameUseCase,
   val saveClientNameUseCase: SaveClientNameUseCase,
       getAdresUseCase: GetAdresUseCase,
   val saveAdresUseCase: SaveAdresUseCase,
   val workWithKorzinaUseCase: WorkWithKorzinaUseCase

): ViewModel(){

    var recyclerKorsinaLiveData = MutableLiveData<RecyclerView>()
    var allItogLiveData = MutableLiveData<Int>()
    var btRegisterOrderVisibility = MutableLiveData<Boolean>()
    var textForClientVisibility = MutableLiveData<Boolean>()
    var clientNametVisibility = MutableLiveData<Boolean>()
    var btDostavkaVisibility = MutableLiveData<Boolean>()
    var btSamovivozVisibility = MutableLiveData<Boolean>()
    var editTextAdresVisibility = MutableLiveData<Boolean>()
    var radioKorsinaGroupVisibility = MutableLiveData<Boolean>()
    var buttonZakazVisibility = MutableLiveData<Boolean>()
    var textViewDateVisibility = MutableLiveData<Boolean>()
    var textViewTimeVisibility = MutableLiveData<Boolean>()
    var rcViewKorsinaVisibility = MutableLiveData<Boolean>()
    var buttonClearVisibility = MutableLiveData<Boolean>()
    var dateLiveData = MutableLiveData<String>()
    var timeLiveData = MutableLiveData<String>()
    var clientNameLiveData = MutableLiveData<ClientNameModelDomain>()
    var adresLiveData = MutableLiveData<AdresModelDomain>()
    var typefaceDostavkaLiveData = MutableLiveData<Typeface>()
    var typefaceSamovivozLiveData = MutableLiveData<Typeface>()

    lateinit var recyclerKorsina: RecyclerView
    lateinit var adapterKorsina: KorsinaAdapter

    var finishedKorsinsList = ArrayList<ItemKorsina>()

    init{
        typefaceDostavkaLiveData.value = Typeface.defaultFromStyle(Typeface.NORMAL)
        typefaceSamovivozLiveData.value = Typeface.defaultFromStyle(Typeface.NORMAL)

        btRegisterOrderVisibility.value = true
        textForClientVisibility.value = false
        clientNametVisibility.value = false
        btDostavkaVisibility.value = false
        btSamovivozVisibility.value = false
        editTextAdresVisibility.value = false
        radioKorsinaGroupVisibility.value = false
        buttonZakazVisibility.value = false
        textViewDateVisibility.value = false
        textViewTimeVisibility.value = false
        rcViewKorsinaVisibility.value = true
        buttonClearVisibility.value = true
        dateLiveData.value =""
        timeLiveData.value =""

        clientNameLiveData.value = getClientNameUseCase.execute()
        adresLiveData.value = getAdresUseCase.execute()

        allItogLiveData.value = workWithKorzinaUseCase.allItog()
    }
    fun beforeSending(){
        finishedKorsinsList = workWithKorzinaUseCase.getBasket().korsinaList
    }

    fun setDostavkaTypeface(style: Int){
        typefaceDostavkaLiveData.value = Typeface.defaultFromStyle(style)
    }
    fun setSamovivizTypeface(style: Int){
        typefaceSamovivozLiveData.value = Typeface.defaultFromStyle(style)
    }
    fun setBtRegisterOrderVisibility(boolean: Boolean){
        btRegisterOrderVisibility.value = boolean
    }
    fun setTextForClientVisibility(boolean: Boolean){
        textForClientVisibility.value = boolean
    }
    fun setClientNameVisibility(boolean: Boolean){
        clientNametVisibility.value = boolean
    }
    fun setBtDostavkaVisibility(boolean: Boolean){
        btDostavkaVisibility.value = boolean
    }
    fun setBtSamovivozVisibility(boolean: Boolean){
        btSamovivozVisibility.value = boolean
    }
    fun setEditTextAdresVisibility(boolean: Boolean){
        editTextAdresVisibility.value = boolean
    }
    fun setRadioKorsinaGroupVisibility(boolean: Boolean){
        radioKorsinaGroupVisibility.value = boolean
    }
    fun setButtonZakazVisibility(boolean: Boolean){
        buttonZakazVisibility.value = boolean
    }
    fun setTextViewDateVisibility(boolean: Boolean){
        textViewDateVisibility.value = boolean
    }
    fun setTextViewTimeVisibility(boolean: Boolean){
        textViewTimeVisibility.value = boolean
    }
    fun setRcViewKorsinaVisibility(boolean: Boolean){
        rcViewKorsinaVisibility.value = boolean
    }
    fun setButtonClearVisibility(boolean: Boolean){
        buttonClearVisibility.value = boolean
    }
    fun setDataLiveData(date: String){
        dateLiveData.value = date
    }
    fun setTimeLiveData(time: String){
        timeLiveData.value = time
    }
    fun saveClientName( name: String){
        val clientNameModelDomain = ClientNameModelDomain(text = name)
        saveClientNameUseCase.execute(clientNameModelDomain)
    }
    fun saveAdres( adres: String){
        val adresModelDomain = AdresModelDomain(text = adres)
        saveAdresUseCase.execute(adresModelDomain)
    }
    fun initAdapter(recycViewKorsina: RecyclerView, adaptKorsina: KorsinaAdapter){
        recyclerKorsina = recycViewKorsina
        adapterKorsina = adaptKorsina
        showKorsina()
    }
    fun showKorsina(){
        recyclerKorsinaLiveData.value = recyclerKorsina
        recyclerKorsinaLiveData.value!!.adapter =  adapterKorsina
        adapterKorsina.addAll(workWithKorzinaUseCase.getBasket().korsinaList)
    }
    fun clearKorsina(){
        adapterKorsina.clearAll()
        workWithKorzinaUseCase.clearBasket()
    }
    fun deleteItemList(pos: Int){
        adapterKorsina.removeItem(pos)
        allItogLiveData.value =  workWithKorzinaUseCase.deleteItemBasket(pos)
    }
    fun setCount(pos: Int, itemKorsina: ItemKorsina, plusOrMinus: String){
        val newItemKorsina = workWithKorzinaUseCase.setShtuk(itemKorsina, plusOrMinus)
        val kosinaDomainModel = workWithKorzinaUseCase.getBasket()
        kosinaDomainModel.korsinaList[pos] = newItemKorsina
        workWithKorzinaUseCase.saveBasket(kosinaDomainModel)
        var allItog = 0
        for(x in kosinaDomainModel.korsinaList.indices){
            allItog += kosinaDomainModel.korsinaList[x].itog
        }
        allItogLiveData.value = allItog
        adapterKorsina.replace(newItemKorsina,pos)
    }
    fun sendToVadim(creatFolderModelDomain: CreatFolderModelDomain){
        creatVadimFolderUseCase.execute(creatFolderModelDomain)
    }
}

