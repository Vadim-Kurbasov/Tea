package com.example.tea.domain.usecase

import com.example.tea.domain.models.KosinaDomainModel
import com.example.tea.domain.repository.DomainKorsinaInterface
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina

class GetKorsinaUseCase(private val domainKorsinaInterface: DomainKorsinaInterface) {

    fun execute():KosinaDomainModel {
      val strFromMemoryModelDomain = domainKorsinaInterface.getKorsinuToMemory()
        val kosinaDomainModel = doFinishArrayKorsin(strFromMemoryModelDomain.str)
        return kosinaDomainModel
    }

    // Функция преобразует строу из памяти телефона в List Корзины
    fun doFinishArrayKorsin(a: String): KosinaDomainModel {

        var finishedKorsinsLis = ArrayList<ItemKorsina>()
        val c = a
            .replace("[", "")
            .replace("]", "")
            .replace("(", "")
            .replace(")", "")

        val aArray = c.split("ItemKorsina").toTypedArray()
        for (i in 1 until aArray.size) {
            var bb = aArray[i].replace(")", "")
            var bArray = bb.split(",").toTypedArray()

            var item = ItemKorsina("", 1, 1, 1, "","")

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
