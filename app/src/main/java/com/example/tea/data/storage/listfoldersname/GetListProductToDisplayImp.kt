package com.example.tea.data.storage.listfoldersname

import android.os.Handler
import android.os.Looper
import com.example.tea.data.storage.GetListProductToDisplayInterface
import com.example.tea.data.models.GetListProductToDisplayModel
import com.example.tea.data.models.Product
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

class GetListProductToDisplayImp: GetListProductToDisplayInterface {

    override fun getListProduct(getListProductToDisplayModel: GetListProductToDisplayModel) {
        GlobalScope.launch() {
            val url: URL
            var connection: HttpsURLConnection? = null
            try {
                url = URL("https://cloud-api.yandex.net/v1/disk/resources?path=disk:/Продукция/"+ getListProductToDisplayModel.choisenFolder +"&limit=100")
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "OAuth " + getListProductToDisplayModel.token)
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
                    takePrises(names, getListProductToDisplayModel)
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

    fun takePrises( names :String, getListProductToDisplayModel: GetListProductToDisplayModel) {
        GlobalScope.launch() {
            val url: URL
            var connection: HttpsURLConnection? = null
            try {
                url = URL("https://cloud-api.yandex.net/v1/disk/resources?path=disk:/Продукция/"+ getListProductToDisplayModel.choisenFolder + "/"+ names)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "OAuth " + getListProductToDisplayModel.token)
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
                val st = jsonArray[0].toString()
                val tempJson = JSONObject(st)
                val st1 = jsonArray[2].toString()
                val tempJson1 = JSONObject(st1)
                val prise = tempJson.getString("name").toInt()
                val imageName = tempJson1.getString("name")
                getUrlForDouloadImage(names, prise, imageName, getListProductToDisplayModel)
            }
            catch (e: FileNotFoundException) {e.printStackTrace() }
            catch (e: IOException) { e.printStackTrace() }
            catch (e: JSONException) { e.printStackTrace() }
            finally { connection?.disconnect() }
        }
    }

    private fun getUrlForDouloadImage(name: String, prise: Int, imageName: String,getListProductToDisplayModel: GetListProductToDisplayModel) {

        GlobalScope.launch() {

            val url: URL
            var connection: HttpsURLConnection? = null
            try {
                url = URL("https://cloud-api.yandex.net/v1/disk/resources/download?path=disk:/Продукция/"+ getListProductToDisplayModel.choisenFolder +  "/" + name + "/" + imageName)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "OAuth " + getListProductToDisplayModel.token)
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
                    val product = Product(href,name,prise )

                    if(getListProductToDisplayModel.choisenFolder=="Чай") {
                        Object.teaVM.showPlant(product)
                    }
                    if(getListProductToDisplayModel.choisenFolder=="Кофе") {
                        Object.kofeVM.showPlant(product)
                    }
               // }, 100)
                }, 1)
            }
            catch (e: FileNotFoundException) {e.printStackTrace() }
            catch (e: IOException) { e.printStackTrace() }
            catch (e: JSONException) { e.printStackTrace() }
            finally { connection?.disconnect() }
        }
    }
}