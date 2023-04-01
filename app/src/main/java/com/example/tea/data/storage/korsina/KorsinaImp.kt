package com.example.tea.data.storage.korsina

import android.content.Context
import com.example.tea.data.storage.KorsinaInterface
import com.example.tea.data.models.KorsinaModel
import com.example.tea.data.models.StrFromMemoryModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KorsinaImp(val context: Context): KorsinaInterface {
    override fun saveKorsina(korsinaModel: KorsinaModel) {
        runBlocking {
            val job =  GlobalScope.launch {
                KorsinaStoreManager.saveValue(context, "testKey", korsinaModel.korsinaList)
            }
            job.join() // ждем завершения вложенной сопрограммы
        }
    }

    override fun getKorsina(): StrFromMemoryModel {

        var strFromMemoryModel= StrFromMemoryModel("")

        runBlocking {
            val job = GlobalScope.launch {
                // Достаем из памяти корзину в виде строки и преобразуем ее в новый List корзины
                val a = KorsinaStoreManager.getValue(context, "testKey")
                strFromMemoryModel = StrFromMemoryModel(str = a)
            }
            job.join() // ждем завершения вложенной сопрограммы
        }
        return strFromMemoryModel
    }
}

