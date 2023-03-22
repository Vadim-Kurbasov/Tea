package com.example.tea.data.storage.linktodownloadimage

import android.os.Handler
import android.os.Looper
import com.example.tea.data.storage.DownloadLinkInterface
import com.example.tea.data.models.LinkToDownloadImageModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LinkToDownloadImageImp : DownloadLinkInterface{

   override  fun downLoadImage(linkToDownloadImageModel: LinkToDownloadImageModel) {

        GlobalScope.launch() {
            val url: URL
            var connection: HttpsURLConnection? = null
            try {
                url = URL("https://cloud-api.yandex.net/v1/disk/resources?path=disk:/Продукция/"+ linkToDownloadImageModel.choisenFolder + "/" + linkToDownloadImageModel.nameFolder)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "OAuth " + linkToDownloadImageModel.token)
                val br = BufferedReader(
                    InputStreamReader(
                        connection.inputStream
                    )
                )
                val line = br.readLine()
                val jsonObject =
                    JSONObject(line) //Помещаем полученную из Яндекса строку в jsonObject
                val j = jsonObject["_embedded"] as JSONObject
                val jsonArray = j["items"] as JSONArray
                for (i in 0 until jsonArray.length()) {

                    val st = jsonArray[i].toString()
                    val tempJson = JSONObject(st)
                    val names = tempJson.getString("name")

                    if(i!=0 && i!=1){
                        getUrlForDouloadImage(names, linkToDownloadImageModel)
                    }
                    if(i==1){
                        Handler(Looper.getMainLooper()).postDelayed({
                            LinkDowloadObject.showProductViewModel.setDescribeFolder(names)
                        }, 100)
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun getUrlForDouloadImage(nameImage: String, linkToDownloadImageModel: LinkToDownloadImageModel) {
        GlobalScope.launch() {

            val url: URL
            var connection: HttpsURLConnection? = null
            try {
                url =
                    URL("https://cloud-api.yandex.net/v1/disk/resources/download?path=disk:/Продукция/" +linkToDownloadImageModel.choisenFolder + "/" + linkToDownloadImageModel.nameFolder + "/" + nameImage)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection!!.setRequestProperty("Authorization", "OAuth " + linkToDownloadImageModel.token)
                val br = BufferedReader(
                    InputStreamReader(
                        connection.inputStream
                    )
                )
                val line = br.readLine()
                val publishLink =
                    JSONObject(line) //Помещаем полученную из Яндекса строку в jsonObject
                val href = publishLink.getString("href") //Парсим по ключу

                Handler(Looper.getMainLooper()).postDelayed({
                    LinkDowloadObject.showProductViewModel.setHrefList(href)
                }, 100)
            }
            catch (e: FileNotFoundException) {e.printStackTrace() }
            catch (e: IOException) { e.printStackTrace() }
            catch (e: JSONException) { e.printStackTrace() }
            finally { connection?.disconnect() }
        }.start()
    }
}
