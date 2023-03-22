package com.example.tea.data.storage.GoogleSiteToken

import android.os.Handler
import android.os.Looper
import com.example.tea.data.storage.GoogleSiteTokenInterface
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class GoogleSiteTokenImp(private val  googleSiteTokenViewModel: GoogleSiteTokenViewModel): GoogleSiteTokenInterface {

    override  fun getTokenFromGoogleSite() {
                var token = ""
                GlobalScope.launch() {
                try {
                   val doc: Document =
                   Jsoup.connect("https://sites.google.com/view/y0agaaaabozfdraadlwwaaaadczu4q")
                      .get()
                   token = doc.title().toString()
                    Handler(Looper.getMainLooper()).postDelayed({
                    googleSiteTokenViewModel.setTokenLifeData(token)
                    },100)
                } catch (exception: Exception) {
                    Handler(Looper.getMainLooper()).postDelayed({
                    googleSiteTokenViewModel.setTokenLifeData("")
                    },100)
                }
            }
    }
}


