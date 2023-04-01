package com.example.tea.domain.usecase

import com.example.tea.domain.models.KosinaDomainModel
import com.example.tea.domain.repository.DomainKorsinaInterface
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina

class WorkWithKorzinaUseCase(private val domainKorsinaInterface: DomainKorsinaInterface) {

    fun saveBasket(kosinaDomainModel: KosinaDomainModel){
        domainKorsinaInterface.saveKorsinuToMemory(kosinaDomainModel)
    }

    fun getBasket():KosinaDomainModel{
        val strFromMemoryModelDomain = domainKorsinaInterface.getKorsinuToMemory()
        val kosinaDomainModel = doFinishArrayKorsin(strFromMemoryModelDomain.str)
        return kosinaDomainModel
    }

    fun putitemBasket(itemKorsina: ItemKorsina){
        val finishKosinaDomainModel = getBasket()
        finishKosinaDomainModel.korsinaList.add(itemKorsina)
        saveBasket(finishKosinaDomainModel)
    }

    fun deleteItemBasket(pos: Int): Int{
        val finishKosinaDomainModel = getBasket()
        if(finishKosinaDomainModel.korsinaList.size > pos) {
            finishKosinaDomainModel.korsinaList.removeAt(pos)
        }
        saveBasket(finishKosinaDomainModel)
        var allItog = 0
        for(x in finishKosinaDomainModel.korsinaList.indices){
            allItog += finishKosinaDomainModel.korsinaList[x].itog
        }
        return allItog
    }

    fun clearBasket(){
        val finishKosinaDomainModel = getBasket()
        finishKosinaDomainModel.korsinaList.clear()
        saveBasket(finishKosinaDomainModel)
    }

    fun allItog(): Int{
        val finishKosinaDomainModel = getBasket()
        var allItog = 0
        for(x in finishKosinaDomainModel.korsinaList.indices){
            allItog += finishKosinaDomainModel.korsinaList[x].itog
        }
        return allItog
    }

    fun setShtuk( itemKorsina: ItemKorsina, plusOrMinus: String): ItemKorsina{

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
        return itemKorsina
    }
    // Функция преобразует строу из памяти телефона в List Корзины
    fun doFinishArrayKorsin(a: String): KosinaDomainModel {

        val finishedKorsinsLis = ArrayList<ItemKorsina>()
        val c = a
            .replace("[", "")
            .replace("]", "")
            .replace("(", "")
            .replace(")", "")

        val aArray = c.split("ItemKorsina").toTypedArray()
        for (i in 1 until aArray.size) {
            val bb = aArray[i].replace(")", "")
            val bArray = bb.split(",").toTypedArray()

            val item = ItemKorsina("", 1, 1, 1, "","")

            for (f in 0 until bArray.size) {
                if (f == 0) {
                    item.name = bArray[0].replace("name=", "")
                }
                if (f == 1) {
                    item.count = bArray[1]
                        .replace("count=", "")
                        .replace(" ", "")
                        .toInt()
                }
                if (f == 2) {
                    item.cena = bArray[f]
                        .replace("cena=", "")
                        .replace(" ", "")
                        .toLong().toInt()
                }
                if (f == 3) {
                    item.itog = bArray[f]
                        .replace("itog=", "")
                        .replace(" ", "")
                        .toLong().toInt()
                }
                if (f == 4) {
                    item.number = bArray[f].replace("number=", "")
                }
            }
            finishedKorsinsLis.add(item)
        }
        val kosinaDomainModel = KosinaDomainModel(korsinaList = finishedKorsinsLis)
        return kosinaDomainModel
    }
}

