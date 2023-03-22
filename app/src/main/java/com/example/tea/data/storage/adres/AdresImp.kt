package com.example.tea.data.storage.adres

import android.content.Context
import com.example.tea.data.models.AdresModel
import com.example.tea.data.storage.AdresInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AdresImp (val context: Context) : AdresInterface {

    override fun saveAdres(adresModel: AdresModel) {
        GlobalScope.launch {
            AdresStoreManager.saveValue(context, "adresKey", adresModel)
        }
    }

    override fun getAdres(): AdresModel {

        var adresModel= AdresModel("")

        runBlocking {
            val job = GlobalScope.launch {
                // Достаем из памяти корзину в виде строки и преобразуем ее в новый List корзины
                val a = AdresStoreManager.getValue(context, "adresKey")
                adresModel = AdresModel(text = a)
            }
            job.join() // ждем завершения вложенной сопрограммы
        }
        return adresModel
    }
}