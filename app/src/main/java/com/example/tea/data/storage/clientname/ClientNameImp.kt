package com.example.tea.data.storage.clientname


import android.content.Context
import com.example.chocolate.data.storage.greetingtext.GreetingStoreManager
import com.example.tea.data.models.ClientNameModel
import com.example.tea.data.models.GreetingTextModel
import com.example.tea.data.storage.ClientNameInterface
import com.example.tea.data.storage.GreetingTextInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ClientNameImp(val context: Context) : ClientNameInterface {

    override fun saveClientName(clientNameModel: ClientNameModel) {
        GlobalScope.launch {
            ClientNameStoreManager.saveValue(context, "clientNameKey", clientNameModel)
        }
    }

    override fun getClientName(): ClientNameModel {

        var clientNameModel= ClientNameModel("")

        runBlocking {
            val job = GlobalScope.launch {
                // Достаем из памяти корзину в виде строки и преобразуем ее в новый List корзины
                val a = ClientNameStoreManager.getValue(context, "clientNameKey")
                clientNameModel = ClientNameModel(text = a)
            }
            job.join() // ждем завершения вложенной сопрограммы
        }
        return clientNameModel
    }
}