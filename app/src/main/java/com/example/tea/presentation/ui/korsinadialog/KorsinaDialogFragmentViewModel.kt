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
   val getKorsinuUseCase: GetKorsinaUseCase,
   val saveKorsinuUseCase: SaveKorsinuUseCase,
   val creatVadimFolderUseCase: CreatVadimFolderUseCase,
   val showTostUseCase: ShowTostUseCase,
   val getClientNameUseCase: GetClientNameUseCase,
   val saveClientNameUseCase: SaveClientNameUseCase,
   val getAdresUseCase: GetAdresUseCase,
   val saveAdresUseCase: SaveAdresUseCase

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
    var kosinaDomainModel: KosinaDomainModel

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

        kosinaDomainModel = getKorsinuUseCase.execute()
        finishedKorsinsList = kosinaDomainModel.korsinaList
        allItog(finishedKorsinsList)
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
        adapterKorsina.addAll(finishedKorsinsList)
    }

    fun clearKorsina(){
        finishedKorsinsList.clear()
        kosinaDomainModel.korsinaList = finishedKorsinsList
        allItog(finishedKorsinsList)
        saveKorsinuUseCase.execute(kosinaDomainModel)
        adapterKorsina.addAll(finishedKorsinsList)
        showKorsina()
        showTostUseCase.execute("Корзина пуста")
    }

    fun deleteItemList(pos: Int){
        var newFinishedKorsinsList = ArrayList<ItemKorsina>()
        kosinaDomainModel = getKorsinuUseCase.execute()
        newFinishedKorsinsList = kosinaDomainModel.korsinaList
        newFinishedKorsinsList.removeAt(pos)
        finishedKorsinsList = newFinishedKorsinsList
        allItog(finishedKorsinsList)
        kosinaDomainModel.korsinaList = finishedKorsinsList
        saveKorsinuUseCase.execute(kosinaDomainModel)
        showKorsina()
        if(finishedKorsinsList.isEmpty()){
            showTostUseCase.execute("Корзина пуста")
        }
    }

    fun setCount(pos: Int, itemKorsina: ItemKorsina, plusOrMinus: String){

        if(plusOrMinus == "plus"){
            if(itemKorsina.number.trim() == "Чай"|| itemKorsina.number.trim() == "Кофе"){
                val coeff = itemKorsina.cena / 2
                itemKorsina.itog = itemKorsina.itog + coeff
                itemKorsina.count = itemKorsina.count + 50
            }
            else {
                itemKorsina.itog = itemKorsina.itog + itemKorsina.cena
                itemKorsina.count = itemKorsina.count + 1
            }
        }
        if(plusOrMinus == "minus" && itemKorsina.count >1){
            if(itemKorsina.number.trim() == "Чай"|| itemKorsina.number.trim() == "Кофе"){
                val coeff = itemKorsina.cena / 2
                if(itemKorsina.count >50) {
                    itemKorsina.itog = itemKorsina.itog - coeff
                    itemKorsina.count = itemKorsina.count - 50
                }
            }
            else {
                itemKorsina.itog = itemKorsina.itog - itemKorsina.cena
                itemKorsina.count = itemKorsina.count - 1
            }
        }

        finishedKorsinsList[pos] = itemKorsina
        allItog(finishedKorsinsList)
        adapterKorsina.replace(itemKorsina,pos)
        kosinaDomainModel.korsinaList = finishedKorsinsList
        saveKorsinuUseCase.execute(kosinaDomainModel)
    }

    fun allItog(list: ArrayList<ItemKorsina>){ // Подсчитывает общую сумму заказа
        var allItog:Int =0
        for(x in list.indices){
            allItog += list[x].itog
        }
        allItogLiveData.value =allItog
    }

    fun sendToVadim(creatFolderModelDomain: CreatFolderModelDomain){
        creatVadimFolderUseCase.execute(creatFolderModelDomain)
    }
}


//    fun sendOrderWhatsApp(clientName: String,
//                          dostavkaOrSamovivoz: String,
//                          adresdostavkaOrSamovivoz: String,
//                          dateTime: String){
//        var strToSend:String = ""
//
//        for(i in finishedKorsinsList.indices) run {
//            strToSend += finishedKorsinsList[i].name +
//                    " - Цена: " + finishedKorsinsList[i].cena +
//                    ", Шт: "+ finishedKorsinsList[i].count +
//                    ", Сумма: " + finishedKorsinsList[i].itog +"\n"
//        }
//
//        strToSend+= "Итог: " + allItogLiveData.value + " р." + "\n"+
//                clientName + "\n"+
//                dostavkaOrSamovivoz + ":" + " " + adresdostavkaOrSamovivoz + "\n"+
//                dateTime
//
//        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date()).replace(":", "-")
//
//        val creatFolderModelDomain = CreatFolderModelDomain(
//            "y0_AgAAAAAOOV-mAADLWwAAAADbEsfXQiTanJTcSSy6UjpR8FNxpO14wEs",
//            "Заказы/Чай/" + allItogLiveData.value + "р " + date
//        )
//        creatVadimFolderUseCase.execute(creatFolderModelDomain)
//
//        val whatsAppModelDomain = WhatsAppModelDomain(strToSend = strToSend)
//        //whatsAppUseCase.execute(whatsAppModelDomain)
//    }

