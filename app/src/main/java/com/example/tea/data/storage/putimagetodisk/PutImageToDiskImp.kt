package com.example.tea.data.storage.putimagetodisk

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.example.tea.data.storage.PutImageToDiskInterface
import com.example.tea.data.models.PutImageToDiskModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PutImageToDiskImp(private val  putImageToDiskViewModel: PutImageToDiskViewModel) : PutImageToDiskInterface{

   override fun putImageToDisk(putImageToDiskModel: PutImageToDiskModel) {
        GlobalScope.launch() {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val src: ImageDecoder.Source = ImageDecoder.createSource(putImageToDiskModel.contentResolver, putImageToDiskModel.uri!!)
                ImageDecoder.decodeBitmap(src)
            }
            else{
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(putImageToDiskModel.contentResolver, putImageToDiskModel.uri)
            }

            val url: URL
            var bytes2=  compressBitmap(bitmap,1) // 10 - качество фото
            var connection: HttpsURLConnection? = null
            try {
                url = URL(putImageToDiskModel.href)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "PUT"
                val out = DataOutputStream(connection!!.outputStream)
                out.write(bytes2)
                out.close()
                connection.inputStream

                Handler(Looper.getMainLooper()).postDelayed({
                      putImageToDiskViewModel.setViewModel(true)
                },100)


            } catch (e: FileNotFoundException) {
                Handler(Looper.getMainLooper()).postDelayed({
                    putImageToDiskViewModel.setViewModel(false)
                },100)
            } catch (e: IOException) {
                Handler(Looper.getMainLooper()).postDelayed({
                    putImageToDiskViewModel.setViewModel(false)
                },100)
                e.printStackTrace()
            } finally { connection?.disconnect() }
        }.start()
    }

    // Уменьшение размера фото
    private fun compressBitmap(bitmap: Bitmap, quality:Int): ByteArray {
        // Initialize a new ByteArrayStream
        val stream = ByteArrayOutputStream()
        // Compress the bitmap with JPEG format and specified quality
        bitmap.compress(
            Bitmap.CompressFormat.JPEG, quality, stream
        )
        val byteArray = stream.toByteArray()
        return byteArray
    }
}