package com.example.tea.data.storage.linktouploadimage

import android.util.Log
import com.example.tea.data.storage.LinkToUploadImageInterface
import com.example.tea.data.models.HrefModel
import com.example.tea.data.models.LinkToUploadImageModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LinkToUploadImageImp: LinkToUploadImageInterface {
    override fun getLinkTiUploadImage(linkToUploadImageModel: LinkToUploadImageModel): HrefModel {
        var hrefModel = HrefModel("")
        runBlocking {
            val job =  GlobalScope.launch() {
                val url: URL
                var connection: HttpsURLConnection? = null
                try {
                    url =
                        URL("https://cloud-api.yandex.net/v1/disk/resources/upload?path=${linkToUploadImageModel.path}")
                    connection = url.openConnection() as HttpsURLConnection
                    connection.setRequestProperty(
                        "Authorization",
                        "OAuth " + "${linkToUploadImageModel.token}"
                    )
                    val br = BufferedReader(
                        InputStreamReader(
                            connection!!.inputStream
                        )
                    )
                    val line = br.readLine()
                    val hrefJson =
                        JSONObject(line) //Помещаем полученную из Яндекса строку в jsonObject
                    hrefModel = HrefModel(href = hrefJson.getString("href")) //Парсим по ключу
                } catch (exception: Exception) {
                    Log.d("Ошибка getLinkTiUploadImage", "Ошибка getLinkTiUploadImage")
                }
            }
            job.join() // ждем завершения вложенной сопрограммы
        }
        return hrefModel
    }
}