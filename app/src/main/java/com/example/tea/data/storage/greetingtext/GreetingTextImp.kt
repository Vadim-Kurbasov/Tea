package com.example.chocolate.data.storage.greetingtext

import android.content.Context
import com.example.tea.data.models.GreetingTextModel
import com.example.tea.data.storage.GreetingTextInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GreetingTextImp(val context: Context) : GreetingTextInterface {

    override fun saveTextGreeting(greetingTextModel: GreetingTextModel) {
        GlobalScope.launch {
            GreetingStoreManager.saveValue(context, "greetingKey", greetingTextModel)
        }
    }

    override fun getTextGreeting(): GreetingTextModel {

        var greetingTextModel= GreetingTextModel("")

        runBlocking {
            val job = GlobalScope.launch {
                // Достаем из памяти корзину в виде строки и преобразуем ее в новый List корзины
                val a = GreetingStoreManager.getValue(context, "greetingKey")
                greetingTextModel = GreetingTextModel(text = a)
            }
            job.join() // ждем завершения вложенной сопрограммы
        }
        return greetingTextModel
    }
}