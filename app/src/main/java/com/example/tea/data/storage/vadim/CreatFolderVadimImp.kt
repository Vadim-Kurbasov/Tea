package com.example.tea.data.storage.vadim

import android.os.Handler
import android.os.Looper
import com.example.tea.data.storage.CreatFolderYaDiskInterface
import com.example.tea.data.models.CreatFolderModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CreatFolderVadimImp: CreatFolderYaDiskInterface {

    override fun creatFolderYaDisk(creatFolderModel: CreatFolderModel){ // Создание папаки Продукция Чай Вадим
            GlobalScope.launch() {
            val url: URL
            var connection: HttpsURLConnection? = null
            try {
                url =
                    URL("https://cloud-api.yandex.net/v1/disk/resources?path=${creatFolderModel.path}")
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "PUT"
                connection!!.setRequestProperty(
                    "Authorization",
                    "OAuth " + creatFolderModel.token
                )
                val br = BufferedReader(
                    InputStreamReader(
                        connection.inputStream
                    )
                )
                Handler(Looper.getMainLooper()).postDelayed({
                },100)
            } catch (exception: Exception) {
                Handler(Looper.getMainLooper()).postDelayed({
                },100)
            }
        }
    }
}